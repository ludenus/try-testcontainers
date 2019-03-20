package qa.company.testcontainers

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import qa.company.config.PostgresDbConfig
import java.io.File
import java.time.Duration


class PostgresDbContainer(val cfg: PostgresDbConfig, val externalAddress: String = "172.17.0.1", initSql: File? = null) : GenericContainer<PostgresDbContainer>("${cfg.imageName}:${cfg.imageTag}") {

    init {
        this
                .withStartupAttempts(cfg.testContainer.startupAttempts)
                .withStartupTimeout(Duration.ofMillis(cfg.testContainer.startupTimeoutMilliseconds))
                .withExposedPorts(cfg.internalPort)
                .withEnv("POSTGRES_DB", cfg.database)
                .withEnv("POSTGRES_USER", cfg.user)
                .withEnv("POSTGRES_PASSWORD", cfg.pass)
                .apply {
                    when {
                        (null == initSql) -> {
                            this.withClasspathResourceMapping(
                                    "postgres-init.sql",
                                    "/docker-entrypoint-initdb.d/postgres-init.sql",
                                    BindMode.READ_ONLY
                            )
                        }
                        else -> {
                            this.withFileSystemBind(
                                    initSql.canonicalPath,
                                    "/docker-entrypoint-initdb.d/${initSql.name}",
                                    BindMode.READ_ONLY
                            )
                        }
                    }
                }.waitingFor(
                        Wait.forListeningPort()
                )
    }


}

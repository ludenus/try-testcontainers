package qa.company

import com.typesafe.config.ConfigBeanFactory
import org.junit.Assert
import org.testng.annotations.*
import qa.company.config.PostgresDbConfig
import qa.company.testcontainers.PostgresDbContainer
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


class DbPostgresTest : AbstractTest() {

    val table = "agent_data_src"
    val postgresDbConfig = ConfigBeanFactory.create(config.getConfig("agent-db-postgres"), PostgresDbConfig::class.java)
    val dockerHostIp = config.getString("dockerHostIp")
    val postgresDbContainer = PostgresDbContainer(postgresDbConfig, dockerHostIp)


    lateinit var dbConnection: Connection

    @BeforeClass
    fun beforeClass() = postgresDbContainer.start()

    @AfterClass
    fun afterClass() = postgresDbContainer.stop()

    @BeforeMethod
    fun beforeMethod() {
        val jdbcUrl = "jdbc:postgresql://${postgresDbContainer.containerIpAddress}:${postgresDbContainer.getMappedPort(postgresDbConfig.internalPort)}/${postgresDbConfig.database}"
        dbConnection = DriverManager.getConnection(jdbcUrl, postgresDbConfig.user, postgresDbConfig.pass)
    }

    @AfterMethod
    fun afterMethod() = dbConnection.close()


    @Test
    fun checkPostgresDB() {
        val qaData = "qaData:${UUID.randomUUID()}"
        dbConnection.insertTestData(table, qaData)
        val selected = dbConnection.selectTestData(table)
        Assert.assertEquals(selected.size, 1)
        Assert.assertEquals(selected[0].second, qaData+"asd")
    }


    fun Connection.insertTestData(table: String, qaData: String) {
        val insertSql = "INSERT INTO $table (qa_data) VALUES (?);"
        val stmt = this.prepareStatement(insertSql)
        stmt.setString(1, qaData)
        stmt.executeUpdate()
    }

    fun Connection.selectTestData(table: String): List<Pair<Long, String>> {
        val stmt = this.createStatement()
        val sql = "SELECT * FROM $table"
        val resultSet = stmt.executeQuery(sql)

        val records: MutableList<Pair<Long, String>> = mutableListOf()
        while (resultSet.next()) {
            val row = Pair(resultSet.getLong(1), resultSet.getString(2))
            records.add(row)
        }
        return records
    }

}

package qa.company.config

class PostgresDbConfig {
    var imageName: String = "postgres"
    var imageTag: String = "11.1"
    var internalPort: Int = 5432
    var database: String = "kaa_agent_db"
    var table: String = "agent_data_src"
    var user: String = "postgres"
    var pass: String = "********"

    var testContainer: TestContainerConfig = TestContainerConfig()
}


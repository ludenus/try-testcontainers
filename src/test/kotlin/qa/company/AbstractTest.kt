package qa.company

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

abstract class AbstractTest {
    val log = LoggerFactory.getLogger(this.javaClass.name)
    val config = ConfigFactory.load("test.conf")

}

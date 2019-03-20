plugins {
    kotlin("jvm") version "1.3.11"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-4"
    id("io.qameta.allure") version "2.5"

}

group = "qa.company"
version = "0.0.1"

val test by tasks.getting(Test::class) {
    val testNGOptions = closureOf<TestNGOptions> {
        suites("src/test/resources/testng.xml")
    }

    useTestNG(testNGOptions)
    testLogging.showStandardStreams = true
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {

    compileClasspath("io.qameta.allure:allure-gradle:2.5")

    implementation(kotlin("stdlib", "1.3.11"))

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.11")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.0")

    implementation(kotlin("stdlib", "1.3.11"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.11")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.0")

    implementation("org.slf4j:slf4j-api:1.7.21")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("com.typesafe:config:1.3.3")

    implementation("org.testng:testng:6.13.1")

    implementation("org.testcontainers:testcontainers:1.10.5")
    implementation("org.postgresql:postgresql:42.2.5")

}



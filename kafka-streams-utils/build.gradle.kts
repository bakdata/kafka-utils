description = "Collection of commonly used modules when writing a Kafka Streams Application"

plugins {
    id("java-library")
}

dependencies {
    api(platform(project(":kafka-bom")))
    api(group = "org.apache.kafka", name = "kafka-streams")
    api(group = "org.apache.kafka", name = "kafka-clients")
    implementation(group = "org.jooq", name = "jool", version = "0.9.15")
    implementation(group = "org.slf4j", name = "slf4j-api", version = "2.0.17")

    val junitVersion: String by project
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = junitVersion)
    val assertJVersion: String by project
    testImplementation(group = "org.assertj", name = "assertj-core", version = assertJVersion)
    val mockitoVersion: String by project
    testImplementation(group = "org.mockito", name = "mockito-core", version = mockitoVersion)
    testImplementation(group = "org.mockito", name = "mockito-junit-jupiter", version = mockitoVersion)
    testImplementation(group = "io.github.hakky54", name = "logcaptor", version = "2.10.1")
}

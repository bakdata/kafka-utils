description = "Collection of commonly used modules when writing a Kafka Streams Application"

plugins {
    id("java-library")
}

dependencies {
    api(platform(project(":kafka-bom")))
    api(group = "org.apache.kafka", name = "kafka-streams")
    api(group = "org.apache.kafka", name = "kafka-clients")
    implementation(libs.jool)
    implementation(libs.slf4j)

    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
    testImplementation(libs.logcaptor)
    testImplementation(group = "org.apache.kafka", name = "kafka-streams-test-utils")
}

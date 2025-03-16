description = "BOM for Kafka."

plugins {
    id("java-platform")
}

dependencies {
    constraints {
        val kafkaVersion: String by project
        api("org.apache.kafka:kafka-clients:$kafkaVersion")
        api("org.apache.kafka:kafka_2.13:$kafkaVersion")
        api("org.apache.kafka:kafka-server:$kafkaVersion")
        api("org.apache.kafka:kafka-server-common:$kafkaVersion")
        api("org.apache.kafka:kafka-streams:$kafkaVersion")
        api("org.apache.kafka:kafka-streams-test-utils:$kafkaVersion")
        api("org.apache.kafka:kafka-tools:$kafkaVersion")
        api("org.apache.kafka:connect-api:$kafkaVersion")
        api("org.apache.kafka:connect-transforms:$kafkaVersion")
        api("org.apache.kafka:connect-runtime:$kafkaVersion")
        api("org.apache.kafka:connect-json:$kafkaVersion")
        api("org.apache.kafka:connect-file:$kafkaVersion")
    }
}

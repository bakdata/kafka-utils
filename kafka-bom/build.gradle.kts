description = "BOM for Kafka."

plugins {
    id("java-platform")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        val kafkaVersion: String by project
        api(group = "org.apache.kafka", name = "kafka-clients", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka_2.13", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka-server", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka-server-common", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka-streams", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka-streams-test-utils", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "kafka-tools", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "connect-api", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "connect-transforms", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "connect-runtime", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "connect-json", version = kafkaVersion)
        api(group = "org.apache.kafka", name = "connect-file", version = kafkaVersion)
    }
}

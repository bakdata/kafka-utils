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
        val confluentVersion: String by project
        api("io.confluent:kafka-schema-serializer:$confluentVersion")
        api("io.confluent:kafka-schema-registry-client:$confluentVersion")
        api("io.confluent:kafka-avro-serializer:$confluentVersion")
        api("io.confluent:kafka-protobuf-serializer:$confluentVersion")
        api("io.confluent:kafka-protobuf-provider:$confluentVersion")
        api("io.confluent:kafka-json-schema-serializer:$confluentVersion")
        api("io.confluent:kafka-json-schema-provider:$confluentVersion")
        api("io.confluent:kafka-streams-avro-serde:$confluentVersion")
        api("io.confluent:kafka-streams-protobuf-serde:$confluentVersion")
        api("io.confluent:kafka-streams-json-schema-serde:$confluentVersion")
        api("io.confluent:kafka-connect-avro-converter:$confluentVersion")
        api("io.confluent:kafka-connect-protobuf-converter:$confluentVersion")
        api("io.confluent:kafka-connect-json-schema-converter:$confluentVersion")
    }
}

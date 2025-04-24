description = "BOM for Kafka."

plugins {
    id("java-platform")
}

dependencies {
    constraints {
        api(libs.kafka.clients)
        api(libs.kafka.core)
        api(libs.kafka.server)
        api(libs.kafka.server.common)
        api(libs.kafka.streams)
        api(libs.kafka.streams.test.utils)
        api(libs.kafka.tools)
        api(libs.kafka.connect.api)
        api(libs.kafka.connect.transforms)
        api(libs.kafka.connect.runtime)
        api(libs.kafka.connect.json)
        api(libs.kafka.connect.file)
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

description = "BOM for Confluent."

plugins {
    id("java-platform")
}

dependencies {
    constraints {
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

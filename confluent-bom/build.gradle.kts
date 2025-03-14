description = "BOM for Confluent."

plugins {
    id("java-platform")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        val confluentVersion: String by project
        api(group = "io.confluent", name = "kafka-schema-serializer", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-schema-registry-client", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-avro-serializer", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-protobuf-serializer", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-protobuf-provider", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-json-schema-serializer", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-json-schema-provider", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-streams-avro-serde", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-streams-protobuf-serde", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-streams-json-schema-serde", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-connect-avro-converter", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-connect-protobuf-converter", version = confluentVersion)
        api(group = "io.confluent", name = "kafka-connect-json-schema-converter", version = confluentVersion)
    }
}

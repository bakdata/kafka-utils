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
        api(libs.kafka.schema.serializer)
        api(libs.kafka.schema.registry.client)
        api(libs.kafka.avro.serializer)
        api(libs.kafka.protobuf.serializer)
        api(libs.kafka.protobuf.provider)
        api(libs.kafka.jsonSchema.serializer)
        api(libs.kafka.jsonSchema.provider)
        api(libs.kafka.streams.avro.serde)
        api(libs.kafka.streams.protobuf.serde)
        api(libs.kafka.streams.jsonSchema.serde)
        api(libs.kafka.connect.avro.converter)
        api(libs.kafka.connect.protobuf.converter)
        api(libs.kafka.connect.jsonSchema.converter)
    }
}

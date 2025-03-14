description = "BOM for Kafka utils."

plugins {
    id("java-platform")
}

dependencies {
    constraints {
        api(project(":kafka-streams-utils"))
        api(project(":kafka-bom"))
        api(project(":confluent-bom"))
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "kafka-utils"
include(
    "kafka-streams-utils",
    "kafka-bom",
    "confluent-bom",
    "kafka-utils-bom",
)

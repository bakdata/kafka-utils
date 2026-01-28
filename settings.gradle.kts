pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://central.sonatype.com/repository/maven-snapshots")
    }
}

rootProject.name = "kafka-utils"
include(
    "kafka-streams-utils",
    "kafka-bom",
)

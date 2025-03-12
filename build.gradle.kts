plugins {
    id("com.bakdata.release") version "1.7.1"
    id("com.bakdata.sonar") version "1.7.1"
    id("com.bakdata.sonatype") version "1.9.0"
    id("io.freefair.lombok") version "8.12.2.1"
}

allprojects {
    group = "com.bakdata.kafka"

    tasks.withType<Test> {
        maxParallelForks = 4
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
        maven(url = "https://packages.confluent.io/maven/")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

subprojects {
    plugins.matching { it is JavaPlugin }.all {
        apply(plugin = "io.freefair.lombok")

        configure<JavaPluginExtension> {
            toolchain {
                languageVersion = JavaLanguageVersion.of(11)
            }
        }
    }

    publication {
        developers {
            developer {
                name.set("Philipp Schirmer")
                id.set("philipp94831")
            }
        }
    }
}

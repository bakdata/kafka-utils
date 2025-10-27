plugins {
    alias(libs.plugins.release)
    alias(libs.plugins.sonar)
    alias(libs.plugins.sonatype)
    alias(libs.plugins.lombok)
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
        maven(url = "https://central.sonatype.com/repository/maven-snapshots")
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

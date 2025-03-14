[![Build and Publish](https://github.com/bakdata/kafka-utils/actions/workflows/build-and-publish.yaml/badge.svg)](https://github.com/bakdata/kafka-utils/actions/workflows/build-and-publish.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.bakdata.kafka%3Akafka-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.bakdata.kafka%3Akafka-utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.bakdata.kafka%3Akafka-utils&metric=coverage)](https://sonarcloud.io/dashboard?id=com.bakdata.kafka%3Akafka-utils)
[![Maven](https://img.shields.io/maven-central/v/com.bakdata.kafka/kafka-streams-utils.svg)](https://search.maven.org/search?q=g:com.bakdata.kafka%20AND%20a:kafka-streams-utils&core=gav)

# kafka-utils
Utilities for working with Kafka

## Kafka Streams Utils

Utilities for working with Kafka Streams applications. This library provides:

- `TopologyInformation` to parse Kafka Streams `TopologyDescription` and extract sinks and sources
- Helpers to automatically configure Serdes for Kafka Streams applications

They are mainly used in [streams-bootstrap](https://github.com/bakdata/streams-bootstrap) and [fluent-kafka-streams-tests](https://github.com/bakdata/fluent-kafka-streams-tests), but you are free to use them in your own projects if needed.

### Getting Started

You can add kafka-streams-utils via Maven Central.

#### Gradle

```gradle
implementation group: 'com.bakdata.kafka', name: 'kafka-streams-utils', version: '1.0.0'
```

#### Maven

```xml
<dependency>
    <groupId>com.bakdata.kafka</groupId>
    <artifactId>kafka-streams-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

For other build tools or versions, refer to
the [latest version in MvnRepository](https://mvnrepository.com/artifact/com.bakdata.kafka/kafka-streams-utils/latest).

## Kafka and Confluent BOM

BOM is short for Bill of Materials.
It is a Maven feature that allows you to manage the versions of dependencies in a single place.
This project provides BOM for Kafka dependencies, i.e., Kafka core dependencies in group `org.apache.kafka` as well as Confluent dependencies in group `io.confluent`.

### Getting Started

You can add Kafka/Confluent BOM via Maven Central.

#### Gradle

```gradle
implementation(platform('com.bakdata.kafka:kafka-bom:1.1.0'))
implementation(platform('com.bakdata.kafka:confluent-bom:1.1.0'))
```

#### Maven

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.bakdata.kafka</groupId>
            <artifactId>kafka-bom</artifactId>
            <version>1.1.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
    <dependencies>
        <dependency>
            <groupId>com.bakdata.kafka</groupId>
            <artifactId>confluent-bom</artifactId>
            <version>1.1.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

For other build tools or versions, refer to
the [latest version in MvnRepository](https://mvnrepository.com/artifact/com.bakdata.kafka/kafka-bom/latest).

### Usage

After adding the BOM to your project, you can use the provided dependencies without specifying a version.

#### Gradle

```gradle
implementation group: 'org.apache.kafka' name: 'kafka-streams'
implementation group: 'io.confluent' name: 'kafka-streams-avro-serde'
```

#### Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-streams</artifactId>
    </dependency>
    <dependency>
        <groupId>io.confluent</groupId>
        <artifactId>kafka-streams-avro-serde</artifactId>
    </dependency>
</dependencies>
```

## Development

If you want to contribute to this project, you can simply clone the repository and build it via Gradle.
All dependencies should be included in the Gradle files, there are no external prerequisites.

```bash
> git clone git@github.com:bakdata/kafka-utils.git
> cd kafka-utils && ./gradlew build
```

Please note, that we have [code styles](https://github.com/bakdata/bakdata-code-styles) for Java.
They are basically the Google style guide, with some small modifications.

## Contributing

We are happy if you want to contribute to this project.
If you find any bugs or have suggestions for improvements, please open an issue.
We are also happy to accept your PRs.
Just open an issue beforehand and let us know what you want to do and why.

## License

This project is licensed under the MIT license.
Have a look at the [LICENSE](https://github.com/bakdata/kafka-utils/blob/main/LICENSE) for more details.

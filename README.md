[![Build and Publish](https://github.com/bakdata/kafka-utils/actions/workflows/build-and-publish.yaml/badge.svg)](https://github.com/bakdata/kafka-utils/actions/workflows/build-and-publish.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.bakdata.kafka%3Akafka-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.bakdata.kafka%3Akafka-utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.bakdata.kafka%3Akafka-utils&metric=coverage)](https://sonarcloud.io/dashboard?id=com.bakdata.kafka%3Akafka-utils)
[![Maven](https://img.shields.io/maven-central/v/com.bakdata.kafka/kafka-streams-utils.svg)](https://search.maven.org/search?q=g:com.bakdata.kafka%20AND%20a:kafka-streams-utils&core=gav)

# kafka-utils
Utilities for working with Kafka

These include

- `TopologyInformation` to parse Kafka Streams `TopologyDescription` and extract sinks and sources
- Helpers to automatically configure Serdes for Kafka Streams applications

## Getting Started

### Serde

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

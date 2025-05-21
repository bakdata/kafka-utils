/*
 * MIT License
 *
 * Copyright (c) 2025 bakdata
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bakdata.kafka;

import static com.bakdata.kafka.LoggingConfigurable.RECONFIGURATION_MESSAGE;
import static com.bakdata.kafka.Preconfigured.deserializer;
import static com.bakdata.kafka.Preconfigured.serializer;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import nl.altindag.log.LogCaptor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class PreconfiguredTest {

    @Mock
    private Serde<Object> serde;
    @Mock
    private Serializer<Object> serializer;
    @Mock
    private Deserializer<Object> deserializer;

    static Stream<Arguments> generatePreconfigured() {
        return Stream.of(
                Arguments.of(Preconfigured.create(Serdes.String())),
                Arguments.of(Preconfigured.create(new StringSerializer())),
                Arguments.of(Preconfigured.create(new StringDeserializer()))
        );
    }

    @Test
    void shouldCreateDefaultSerde() {
        assertThat(Preconfigured.defaultSerde().configureForValues(emptyMap())).isNull();
    }

    @Test
    void shouldCreateDefaultSerializer() {
        assertThat(Preconfigured.defaultSerializer().configureForValues(emptyMap())).isNull();
    }

    @Test
    void shouldCreateDefaultDeserializer() {
        assertThat(Preconfigured.defaultDeserializer().configureForValues(emptyMap())).isNull();
    }

    @Test
    void shouldConvertToSerializer() {
        final Preconfigured<Serde<Object>> preconfigured = Preconfigured.create(this.serde);
        when(this.serde.serializer()).thenReturn(this.serializer);
        assertThat(serializer(preconfigured).configureForKeys(emptyMap())).isEqualTo(this.serializer);
    }

    @Test
    void shouldConvertToDeserializer() {
        final Preconfigured<Serde<Object>> preconfigured = Preconfigured.create(this.serde);
        when(this.serde.deserializer()).thenReturn(this.deserializer);
        assertThat(deserializer(preconfigured).configureForKeys(emptyMap())).isEqualTo(this.deserializer);
    }

    @ParameterizedTest
    @MethodSource("generatePreconfigured")
    void shouldLogReconfigurationForValues(final Preconfigured<?> preconfigured) {
        try (final LogCaptor logCaptor = LogCaptor.forClass(LoggingConfigurable.class)) {
            preconfigured.configureForValues(emptyMap());
            assertThat(logCaptor.getWarnLogs()).isEmpty();
            preconfigured.configureForValues(emptyMap());
            assertThat(logCaptor.getWarnLogs())
                    .hasSize(1)
                    .contains(RECONFIGURATION_MESSAGE);
        }
    }

    @ParameterizedTest
    @MethodSource("generatePreconfigured")
    void shouldLogReconfigurationForKeys(final Preconfigured<?> preconfigured) {
        try (final LogCaptor logCaptor = LogCaptor.forClass(LoggingConfigurable.class)) {
            preconfigured.configureForKeys(emptyMap());
            assertThat(logCaptor.getWarnLogs()).isEmpty();
            preconfigured.configureForKeys(emptyMap());
            assertThat(logCaptor.getWarnLogs())
                    .hasSize(1)
                    .contains(RECONFIGURATION_MESSAGE);
        }
    }

    @ParameterizedTest
    @MethodSource("generatePreconfigured")
    void shouldLogReconfigurationMixedValuesFirst(final Preconfigured<?> preconfigured) {
        try (final LogCaptor logCaptor = LogCaptor.forClass(LoggingConfigurable.class)) {
            preconfigured.configureForValues(emptyMap());
            assertThat(logCaptor.getWarnLogs()).isEmpty();
            preconfigured.configureForKeys(emptyMap());
            assertThat(logCaptor.getWarnLogs())
                    .hasSize(1)
                    .contains(RECONFIGURATION_MESSAGE);
        }
    }

    @ParameterizedTest
    @MethodSource("generatePreconfigured")
    void shouldLogReconfigurationMixedKeysFirst(final Preconfigured<?> preconfigured) {
        try (final LogCaptor logCaptor = LogCaptor.forClass(LoggingConfigurable.class)) {
            preconfigured.configureForKeys(emptyMap());
            assertThat(logCaptor.getWarnLogs()).isEmpty();
            preconfigured.configureForValues(emptyMap());
            assertThat(logCaptor.getWarnLogs())
                    .hasSize(1)
                    .contains(RECONFIGURATION_MESSAGE);
        }
    }

}

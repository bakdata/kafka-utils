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

package com.bakdata.kafka.streams.kstream;

import com.bakdata.kafka.Configurator;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.SessionStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.TimestampedKeyValueStore;
import org.apache.kafka.streams.state.TimestampedWindowStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;
import org.apache.kafka.streams.state.VersionedKeyValueStore;
import org.apache.kafka.streams.state.VersionedRecord;
import org.apache.kafka.streams.state.WindowStore;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class StoresXTest {

    @InjectSoftAssertions
    private SoftAssertions softly;

    private static StoresX createStores() {
        return new StoresX(new Configurator(Map.of()));
    }

    @Test
    void shouldCreateKeyValueStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()));
        input.<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<String, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<String, String> inputRecord) {
                        final KeyValueStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(), inputRecord.value());
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<KeyValueStore<String, String>> store = createStores()
                        .keyValueStoreBuilder(Stores.inMemoryKeyValueStore("my-store"), Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final KeyValueStore<String, String> store = driver.getKeyValueStore("my-store");
            this.softly.assertThat(store.get("foo")).isEqualTo("bar");
        }
    }

    @Test
    void shouldCreateSessionStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KTable<Windowed<String>, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()))
                        .groupByKey()
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(60L)))
                        .reduce((v1, v2) -> v1 + v2);
        input.toStream().<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<Windowed<String>, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<Windowed<String>, String> inputRecord) {
                        final SessionStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(), inputRecord.value());
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<SessionStore<String, String>> store = createStores()
                        .sessionStoreBuilder(Stores.inMemorySessionStore("my-store", Duration.ofSeconds(60L)),
                                Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final SessionStore<String, String> store = driver.getSessionStore("my-store");
            this.softly.assertThat(store.fetchSession("foo", 0, 60000)).isEqualTo("bar");
        }
    }

    @Test
    void shouldCreateTimestampedWindowStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()));
        input.<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<String, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<String, String> inputRecord) {
                        final TimestampedWindowStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(),
                                ValueAndTimestamp.make(inputRecord.value(), inputRecord.timestamp()),
                                inputRecord.timestamp());
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<TimestampedWindowStore<String, String>> store = createStores()
                        .timestampedWindowStoreBuilder(
                                Stores.inMemoryWindowStore("my-store", Duration.ofSeconds(60L),
                                        Duration.ofSeconds(60L), false),
                                Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final WindowStore<String, ValueAndTimestamp<String>> store = driver.getTimestampedWindowStore("my-store");
            this.softly.assertThat(store.fetch("foo", 0L)).isEqualTo(ValueAndTimestamp.make("bar", 0L));
        }
    }

    @Test
    void shouldCreateWindowStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()));
        input.<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<String, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<String, String> inputRecord) {
                        final WindowStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(), inputRecord.value(), inputRecord.timestamp());
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<WindowStore<String, String>> store = createStores()
                        .windowStoreBuilder(Stores.inMemoryWindowStore("my-store", Duration.ofSeconds(60L),
                                        Duration.ofSeconds(60L), false), Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final WindowStore<String, String> store = driver.getWindowStore("my-store");
            this.softly.assertThat(store.fetch("foo", 0L)).isEqualTo("bar");
        }
    }

    @Test
    void shouldCreateVersionedKeyValueStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()));
        input.<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<String, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<String, String> inputRecord) {
                        final VersionedKeyValueStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(), inputRecord.value(), inputRecord.timestamp());
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<VersionedKeyValueStore<String, String>> store = createStores()
                        .versionedKeyValueStoreBuilder(
                                Stores.persistentVersionedKeyValueStore("my-store", Duration.ofSeconds(60L)),
                                Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final VersionedKeyValueStore<String, String> store = driver.getVersionedKeyValueStore("my-store");
            this.softly.assertThat(store.get("foo", 0L)).isEqualTo(new VersionedRecord<>("bar", 0L));
        }
    }

    @Test
    void shouldCreateTimestampedKeyValueStore() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input =
                builder.stream("input", Consumed.with(Serdes.String(), Serdes.String()));
        input.<String, String>process(new ProcessorSupplier<>() {
            @Override
            public Processor<String, String, String, String> get() {
                return new SimpleProcessor<>() {
                    @Override
                    public void process(final Record<String, String> inputRecord) {
                        final TimestampedKeyValueStore<String, String> store = this.getStateStore("my-store");
                        store.put(inputRecord.key(),
                                ValueAndTimestamp.make(inputRecord.value(), inputRecord.timestamp()));
                    }
                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                final StoreBuilder<TimestampedKeyValueStore<String, String>> store = createStores()
                        .timestampedKeyValueStoreBuilder(Stores.inMemoryKeyValueStore("my-store"),
                                Serdes.String(),
                                Serdes.String());
                return Set.of(store);
            }
        });
        try (final TopologyTestDriver driver = new TopologyTestDriver(builder.build())) {
            driver.createInputTopic("input", new StringSerializer(), new StringSerializer())
                    .pipeInput("foo", "bar", 0);
            final KeyValueStore<String, ValueAndTimestamp<String>> store =
                    driver.getTimestampedKeyValueStore("my-store");
            this.softly.assertThat(store.get("foo")).isEqualTo(ValueAndTimestamp.make("bar", 0L));
        }
    }
}

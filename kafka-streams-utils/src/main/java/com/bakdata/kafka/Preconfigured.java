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

import static java.util.Collections.emptyMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

/**
 * A pre-configured {@link Serde} or {@link Serializer}, i.e., configs and isKey are set.
 *
 * @param <T> type of underlying configurable
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Preconfigured<T> {
    private final @NonNull Configurable<T> configurable;
    private final @NonNull Map<String, Object> configOverrides;

    private Preconfigured(final Configurable<T> configurable) {
        this(configurable, emptyMap());
    }

    /**
     * Create a pre-configured {@code Serde} that returns {@code null} when calling
     * {@link Preconfigured#configureForKeys(Map)} and {@link Preconfigured#configureForValues(Map)}
     *
     * @param <T> type (de-)serialized by the {@code Serde}
     * @return pre-configured serde
     */
    public static <T> Preconfigured<Serde<T>> defaultSerde() {
        return new Preconfigured<>(new DefaultConfigurable<>());
    }

    /**
     * Pre-configure a {@code Serde}
     *
     * @param serde {@code Serde} to pre-configure
     * @param <S> type of {@link Serde}
     * @param <T> type (de-)serialized by the {@code Serde}
     * @return pre-configured serde
     */
    public static <S extends Serde<T>, T> Preconfigured<S> create(final S serde) {
        return new Preconfigured<>(configurable(serde));
    }

    /**
     * Pre-configure a {@code Serde} with config overrides
     *
     * @param serde {@code Serde} to pre-configure
     * @param configOverrides configs passed to {@link Serde#configure(Map, boolean)}
     * @param <S> type of {@link Serde}
     * @param <T> type (de-)serialized by the {@code Serde}
     * @return pre-configured serde
     */
    public static <S extends Serde<T>, T> Preconfigured<S> create(final S serde,
            final Map<String, Object> configOverrides) {
        return new Preconfigured<>(configurable(serde), configOverrides);
    }

    /**
     * Create a pre-configured {@code Serializer} that returns {@code null} when calling
     * {@link Preconfigured#configureForKeys(Map)} and {@link Preconfigured#configureForValues(Map)}
     *
     * @param <T> type serialized by the {@code Serializer}
     * @return pre-configured serializer
     */
    public static <T> Preconfigured<Serializer<T>> defaultSerializer() {
        return new Preconfigured<>(new DefaultConfigurable<>());
    }

    /**
     * Pre-configure a {@code Serializer}
     *
     * @param serializer {@code Serializer} to pre-configure
     * @param <S> type of {@link Serializer}
     * @param <T> type serialized by the {@code Serializer}
     * @return pre-configured serializer
     */
    public static <S extends Serializer<T>, T> Preconfigured<S> create(final S serializer) {
        return new Preconfigured<>(configurable(serializer));
    }

    /**
     * Pre-configure a {@code Serializer}
     *
     * @param serializer {@code Serializer} to pre-configure
     * @param configOverrides configs passed to {@link Serializer#configure(Map, boolean)}
     * @param <S> type of {@link Serializer}
     * @param <T> type serialized by the {@code Serializer}
     * @return pre-configured serializer
     */
    public static <S extends Serializer<T>, T> Preconfigured<S> create(final S serializer,
            final Map<String, Object> configOverrides) {
        return new Preconfigured<>(configurable(serializer), configOverrides);
    }

    /**
     * Create a pre-configured {@code Deserializer} that returns {@code null} when calling
     * {@link Preconfigured#configureForKeys(Map)} and {@link Preconfigured#configureForValues(Map)}
     *
     * @param <T> type deserialized by the {@code Deserializer}
     * @return pre-configured deserializer
     */
    public static <T> Preconfigured<Deserializer<T>> defaultDeserializer() {
        return new Preconfigured<>(new DefaultConfigurable<>());
    }

    /**
     * Pre-configure a {@code Deserializer}
     *
     * @param deserializer {@code Deserializer} to pre-configure
     * @param <S> type of {@link Deserializer}
     * @param <T> type deserialized by the {@code Deserializer}
     * @return pre-configured deserializer
     */
    public static <S extends Deserializer<T>, T> Preconfigured<S> create(final S deserializer) {
        return new Preconfigured<>(configurable(deserializer));
    }

    /**
     * Pre-configure a {@code Deserializer}
     *
     * @param deserializer {@code Deserializer} to pre-configure
     * @param configOverrides configs passed to {@link Deserializer#configure(Map, boolean)}
     * @param <S> type of {@link Deserializer}
     * @param <T> type deserialized by the {@code Deserializer}
     * @return pre-configured deserializer
     */
    public static <S extends Deserializer<T>, T> Preconfigured<S> create(final S deserializer,
            final Map<String, Object> configOverrides) {
        return new Preconfigured<>(configurable(deserializer), configOverrides);
    }

    /**
     * Convert a pre-configured {@link Serde} to a pre-configured {@link Deserializer}
     *
     * @param preconfigured pre-configured {@link Serde}
     * @param <T> type (de-)serialized by the {@link Serde}
     * @return pre-configured {@link Deserializer} of the {@link Serde}
     */
    public static <T> Preconfigured<Deserializer<T>> asDeserializer(
            final Preconfigured<? extends Serde<T>> preconfigured) {
        return new Preconfigured<>((config, isKey) -> {
            final Serde<T> serde = preconfigured.configurable.configure(config, isKey);
            return serde.deserializer();
        });
    }

    /**
     * Convert a pre-configured {@link Serde} to a pre-configured {@link Serializer}
     *
     * @param preconfigured pre-configured {@link Serde}
     * @param <T> type (de-)serialized by the {@link Serde}
     * @return pre-configured {@link Serializer} of the {@link Serde}
     */
    public static <T> Preconfigured<Serializer<T>> asSerializer(final Preconfigured<? extends Serde<T>> preconfigured) {
        return new Preconfigured<>((config, isKey) -> {
            final Serde<T> serde = preconfigured.configurable.configure(config, isKey);
            return serde.serializer();
        });
    }

    private static <S extends Serde<T>, T> Configurable<S> configurable(final S serde) {
        Objects.requireNonNull(serde, "Use Preconfigured#defaultSerde instead");
        return new LoggingConfigurable<>(new ConfigurableSerde<>(serde));
    }

    private static <S extends Serializer<T>, T> Configurable<S> configurable(final S serializer) {
        Objects.requireNonNull(serializer, "Use Preconfigured#defaultSerializer instead");
        return new LoggingConfigurable<>(new ConfigurableSerializer<>(serializer));
    }

    private static <S extends Deserializer<T>, T> Configurable<S> configurable(final S deserializer) {
        Objects.requireNonNull(deserializer, "Use Preconfigured#defaultDeserializer instead");
        return new LoggingConfigurable<>(new ConfigurableDeserializer<>(deserializer));
    }

    /**
     * Configure for values using a base config
     *
     * @param baseConfig Base config. {@link #configOverrides} override properties of base config.
     * @return configured instance
     */
    public T configureForValues(final Map<String, Object> baseConfig) {
        return this.configure(baseConfig, false);
    }

    /**
     * Configure for keys using a base config
     *
     * @param baseConfig Base config. {@link #configOverrides} override properties of base config.
     * @return configured instance
     */
    public T configureForKeys(final Map<String, Object> baseConfig) {
        return this.configure(baseConfig, true);
    }

    private T configure(final Map<String, Object> baseConfig, final boolean isKey) {
        final Map<String, Object> serializerConfig = this.mergeConfig(baseConfig);
        return this.configurable.configure(serializerConfig, isKey);
    }

    private Map<String, Object> mergeConfig(final Map<String, Object> baseConfig) {
        final Map<String, Object> config = new HashMap<>(baseConfig);
        config.putAll(this.configOverrides);
        return config;
    }

}

package com.bakdata.kafka;

import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
class LoggingConfigurable<T> implements Configurable<T> {

    private final @NonNull Configurable<T> wrapped;
    private boolean configured = false;

    @Override
    public T configure(final Map<String, Object> config, final boolean isKey) {
        if (this.configured) {
            log.warn(
                    "Configurable has already been configured. Configuring twice may lead to unintended side effects.");
        }
        this.configured = true;
        return this.wrapped.configure(config, isKey);
    }
}

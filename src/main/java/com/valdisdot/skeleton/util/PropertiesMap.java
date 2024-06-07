package com.valdisdot.skeleton.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PropertiesMap extends LinkedHashMap<String, Object> {
    public PropertiesMap(Map<String, Object> properties) {
        super(properties);
    }

    public PropertiesMap() {
    }

    public <T> T get(String key, Class<T> asType) {
        try {
            return asType.cast(get(key));
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    public <T> T getOrThrow(String key, Class<T> asType) {
        try {
            if (!containsKey(key)) throw new PropertiesException("No value for key: " + key, asType, null);
            if (get(key) == null) throw new PropertiesException("Value is null for key: " + key, asType, null);
            return asType.cast(get(key));
        } catch (ClassCastException e) {
            throw new PropertiesException(e.getMessage(), asType, get(key));
        }
    }

    public <T> Optional<T> fetch(String key, Class<T> asType) {
        return Optional.ofNullable(get(key, asType));
    }

    public Optional<String> fetchAsString(String key) {
        if (!containsKey(key) || get(key) == null) return Optional.empty();
        return Optional.of(get(key).toString());
    }

    public static class PropertiesException extends RuntimeException {
        private final Class<?> type;
        private final Object object;

        private PropertiesException(String message, Class<?> type, Object property) {
            super(message);
            this.type = type;
            this.object = property;
        }

        public Object getProperty() {
            return object;
        }

        public Class<?> getPropertyType() {
            return type;
        }
    }
}

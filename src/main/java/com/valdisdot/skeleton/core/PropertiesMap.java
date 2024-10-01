package com.valdisdot.skeleton.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The wrapper under the LinkedHashMap. Adds custom method to easy retrieving the needed objects.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class PropertiesMap extends LinkedHashMap<String, Object> {
    public PropertiesMap(Map<String, Object> properties) {
        super(properties);
    }

    public PropertiesMap() {
    }

    /**
     * Returns the object with a simple casting or null.
     *
     * @param <T>    type of the object
     * @param key    the key/ID of the object
     * @param asType class of the type T
     * @return the type
     */
    public <T> T get(String key, Class<T> asType) {
        try {
            return asType.cast(get(key));
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Returns the object with a simple casting or throws an exception.
     *
     * @param <T>    type of the object
     * @param key    the key/ID of the object
     * @param asType class of the type T
     * @return the type
     */
    public <T> T getOrThrow(String key, Class<T> asType) {
        try {
            if (!containsKey(key)) throw new PropertiesException("No value for key: " + key, asType, null);
            if (get(key) == null) throw new PropertiesException("Value is null for key: " + key, asType, null);
            return asType.cast(get(key));
        } catch (ClassCastException e) {
            throw new PropertiesException(e.getMessage(), asType, get(key));
        }
    }

    /**
     * Returns an optional of the object with a simple casting.
     *
     * @param <T>    type of the object
     * @param key    the key/ID of the object
     * @param asType class of the type T
     * @return the type
     */
    public <T> Optional<T> fetch(String key, Class<T> asType) {
        return Optional.ofNullable(get(key, asType));
    }

    /**
     * Returns an optional of a string representation of the object.
     *
     * @param key the key/ID of the object
     * @return the optional of string representation of the value
     */
    public Optional<String> fetchAsString(String key) {
        if (!containsKey(key) || get(key) == null) return Optional.empty();
        return Optional.of(get(key).toString());
    }

    /**
     * Internal RuntimeException, contains the values before the exception was thrown.
     */
    public static class PropertiesException extends RuntimeException {
        private final Class<?> type;
        private final Object object;

        private PropertiesException(String message, Class<?> type, Object property) {
            super(message);
            this.type = type;
            this.object = property;
        }

        /**
         * Returns the casting property.
         *
         * @return the property
         */
        public Object getProperty() {
            return object;
        }

        /**
         * Returns the class, to which the property should have been cast.
         *
         * @return the property type
         */
        public Class<?> getPropertyType() {
            return type;
        }
    }
}

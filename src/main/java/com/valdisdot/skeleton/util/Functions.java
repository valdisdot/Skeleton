package com.valdisdot.skeleton.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The util class which contains default converting functions.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class Functions {
    private Functions() {
    }

    /**
     * Collection to string function.
     *
     * @return the function
     */
    public static Function<Collection<String>, String> collectionToString() {
        return Object::toString;
    }


    /**
     * Collection to json array function.
     *
     * @return the function
     */
    public static Function<Collection<String>, String> collectionToJsonArray() {
        return collection -> collection.stream().collect(Collectors.joining("[", ", ", "]"));
    }
}

package com.valdisdot.skeleton.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Functions {
    private Functions() {
    }

    public static Function<Collection<String>, String> collectionToString() {
        return Object::toString;
    }

    public static Function<Collection<String>, String> collectionToJsonArray() {
        return collection -> collection.stream().collect(Collectors.joining("[", ", ", "]"));
    }
}

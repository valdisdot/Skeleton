package com.valdisdot.util.tool;

import java.util.*;
import java.util.stream.Collectors;

public class ValuesParser {
    //simple String to int
    public static int toInt(String value, boolean replaceAllChars, int defaultValue) {
        if (replaceAllChars) value = value.replaceAll("//D", "");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    //if value contains any non-digit -> defaultValue
    public static int toInt(String value, int defaultValue) {
        return toInt(value, false, defaultValue);
    }

    //["value", "value", "value"]
    public static String toJSONArray(List<?> list) {
        if (Objects.isNull(list) || list.isEmpty()) return "[]";
        StringBuilder builder = new StringBuilder("[");
        list.stream()
                .limit(list.size() - 1)
                .forEach(elem -> builder.append("\"").append(elem).append("\"").append(", "));
        return builder
                .append(list.get(list.size() - 1))
                .append("\"]")
                .toString();
    }

    //no original order
    public static String toJSONArray(Collection<?> collection) {
        return toJSONArray(new ArrayList<>(collection));
    }

    //key: value
    //key: value
    public static String toKeySemicolonValueString(Map<?, ?> map) {
        return map.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "\n").collect(Collectors.joining()).trim();
    }
}

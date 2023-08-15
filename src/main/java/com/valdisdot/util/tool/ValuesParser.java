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

    public static int fromHEXToDecimalInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value.replaceAll("#", ""), 16);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    //["value", "value", "value"]
    public static String toJSONArray(List<?> list) {
        if (Objects.isNull(list) || list.isEmpty()) return "[]";
        if (list.size() > 1) {
            StringBuilder builder = new StringBuilder("[");
            list.stream()
                    .limit(list.size() - 1)
                    .forEach(elem -> builder.append("\"").append(elem).append("\"").append(", "));
            return builder
                    .append("\"")
                    .append(list.get(list.size() - 1))
                    .append("\"]")
                    .toString();
        }
        return "[\"" + list.get(0) + "\"]";
    }

    //no original order
    public static String toJSONArray(Collection<?> collection) {
        return toJSONArray(new ArrayList<>(collection));
    }

    public static String toJSONObject(Map<?,?> map){
        StringBuilder builder = new StringBuilder("{\n");
        ArrayList<Map.Entry<?,?>> entryList = new ArrayList<>(map.entrySet());

        Object temp;
        for(int i = 0; i < entryList.size() - 1; ++i){
            temp = entryList.get(i).getValue();
            builder
                    .append("\t\"")
                    .append(entryList.get(i).getKey())
                    .append("\": ");

            if(temp instanceof Collection) builder.append(toJSONArray((Collection<?>) temp)).append(",\n");
            else if(temp instanceof Map) builder.append(toJSONObject((Map<?, ?>) temp)).append(",\n");
            else builder
                    .append("\"")
                    .append(temp.toString())
                    .append("\",\n");
        }

        temp = entryList.get(entryList.size() - 1).getValue();
        builder
                .append("\t\"")
                .append(entryList.get(entryList.size() - 1).getKey())
                .append("\": ");

        if(temp instanceof Collection) builder.append(toJSONArray((Collection<?>) temp));
        else if(temp instanceof Map) builder.append(toJSONObject((Map<?, ?>) temp));
        else builder
                .append("\"")
                .append(temp.toString())
                .append("\"");

        return builder.append("\n}").toString();
    }
}

package com.valdisdot.util.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

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

    public static String toJSON(Collection<?> collection) {
        try {
            return new ObjectMapper().writeValueAsString(collection);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    public static String toJSON(Map<?,?> map){
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}

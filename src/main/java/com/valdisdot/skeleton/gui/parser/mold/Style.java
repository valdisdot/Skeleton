package com.valdisdot.skeleton.gui.parser.mold;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Style {
    private final Map<Keyword, String> values = new EnumMap<>(Keyword.class);
    private String id;
    private Keyword type;

    public Style() {
        this(null, Keyword.UNKNOWN, null);
    }

    public Style(String id, Keyword type, Map<Keyword, String> values) {
        this.id = id;
        this.type = type;
        if (values != null) this.values.putAll(values);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Keyword getType() {
        return type;
    }

    public void setType(String type) {
        this.type = Keyword.fromValue(type);
    }

    public void setType(Keyword keyword) {
        this.type = Objects.requireNonNull(keyword);
    }

    public Map<Keyword, String> getValues() {
        return Map.copyOf(values);
    }

    public void addValue(String styleKey, String value) {
        this.values.put(Keyword.fromValue(styleKey), value);
    }

    public void addValue(Keyword keyword, String value) {
        this.values.put(Objects.requireNonNull(keyword), value);
    }

    @Override
    public String toString() {
        return "Style{" +
                "values=" + values +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }

    public enum Keyword {
        COLOR("color"),
        BACKGROUND_COLOR("background"),
        FOREGROUND_COLOR("foreground"),
        FONT("font"),
        FONT_NAME("fontName"),
        FONT_SIZE("fontSize"),
        FONT_STYLE_KEY("fontStyle"),
        FONT_STYLE_ITALIC("italic"),
        FONT_STYLE_BOLD("bold"),
        SIZE("size"),
        WIDTH("width"),
        HEIGHT("height"),
        UNKNOWN("unknown");

        private static final Map<String, Keyword> map;

        static {
            map = new HashMap<>();
            map.put(COLOR.getValue(), COLOR);
            map.put(BACKGROUND_COLOR.getValue(), BACKGROUND_COLOR);
            map.put(FOREGROUND_COLOR.getValue(), FOREGROUND_COLOR);
            map.put(FONT.getValue(), FONT);
            map.put(FONT_NAME.getValue(), FONT_NAME);
            map.put(FONT_SIZE.getValue(), FONT_SIZE);
            map.put(FONT_STYLE_KEY.getValue(), FONT_STYLE_KEY);
            map.put(FONT_STYLE_ITALIC.getValue(), FONT_STYLE_ITALIC);
            map.put(FONT_STYLE_BOLD.getValue(), FONT_STYLE_BOLD);
            map.put(SIZE.getValue(), SIZE);
            map.put(WIDTH.getValue(), WIDTH);
            map.put(HEIGHT.getValue(), HEIGHT);
        }

        private final String value;

        private Keyword(String value) {
            this.value = value;
        }

        public static Keyword fromValue(String enumValue) {
            return map.getOrDefault(enumValue, UNKNOWN);
        }

        public String getValue() {
            return value;
        }
    }
}

package com.valdisdot.skeleton.gui.parser.mold;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The class defines a finite set of styling properties like background and foreground colors, font, size.
 */
public class Style implements Identifiable {
    private final Map<Keyword, String> values = new EnumMap<>(Keyword.class);
    private String id;
    private Keyword type;

    /**
     * Instantiates an empty Style.
     */
    public Style() {
        this(null, Keyword.UNKNOWN, null);
    }

    /**
     * Instantiates a new Style.
     *
     * @param id     the id
     * @param type   the type
     * @param values the values
     */
    public Style(String id, Keyword type, Map<Keyword, String> values) {
        this.id = id;
        this.type = type;
        if (values != null) this.values.putAll(values);
    }

    /**{@inheritDoc}*/
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the id for this style.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the keyword of type
     * @see Keyword
     */
    public Keyword getType() {
        return type;
    }

    /**
     * Sets a type keyword by a string value.
     *
     * @param type the type
     * @see Keyword#fromValue(String)
     */
    public void setType(String type) {
        this.type = Keyword.fromValue(type);
    }

    /**
     * Sets type a keyword.
     *
     * @param keyword the keyword
     * @see Keyword
     */
    public void setType(Keyword keyword) {
        this.type = Objects.requireNonNull(keyword);
    }

    /**
     * @return a copy of the values of this style.
     */
    public Map<Keyword, String> getValues() {
        return Map.copyOf(values);
    }

    /**
     * Add a keyword value by a string value, specified for this style.
     *
     * @param styleKey the style key
     * @param value    the value
     * @see Keyword#fromValue(String)
     */
    public void addValue(String styleKey, String value) {
        this.values.put(Keyword.fromValue(styleKey), value);
    }

    /**
     * Add a keyword value, specified for this style.
     *
     * @param keyword the keyword
     * @param value   the value
     * @see Keyword
     */
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

    /**
     * The enumeration of style's keywords.
     */
    public enum Keyword {
        /**
         * Color keyword.
         */
        COLOR("color"),
        /**
         * Background color keyword.
         */
        BACKGROUND_COLOR("background"),
        /**
         * Foreground color keyword.
         */
        FOREGROUND_COLOR("foreground"),
        /**
         * Font keyword.
         */
        FONT("font"),
        /**
         * Font name keyword.
         */
        FONT_NAME("fontName"),
        /**
         * Font size value keyword.
         */
        FONT_SIZE("fontSize"),
        /**
         * Font style value key keyword.
         */
        FONT_STYLE_KEY("fontStyle"),
        /**
         * Font style italic value keyword.
         */
        FONT_STYLE_ITALIC("italic"),
        /**
         * Font style bold value keyword.
         */
        FONT_STYLE_BOLD("bold"),
        /**
         * Size value keyword.
         */
        SIZE("size"),
        /**
         * Width value keyword.
         */
        WIDTH("width"),
        /**
         * Height value keyword.
         */
        HEIGHT("height"),
        /**
         * Unknown value keyword.
         */
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

        /**
         * Util method for getting the keyword from a string.
         *
         * @param enumValue the enum value
         * @return the keyword
         */
        public static Keyword fromValue(String enumValue) {
            return map.getOrDefault(enumValue, UNKNOWN);
        }

        /**
         * @return the string representation of the enumeration.
         */
        public String getValue() {
            return value;
        }
    }
}

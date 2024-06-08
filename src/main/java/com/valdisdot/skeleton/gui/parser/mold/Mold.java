package com.valdisdot.skeleton.gui.parser.mold;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Mold implements Identifiable {
    protected String id;
    protected String title;
    protected Map<String, String> properties;
    protected List<Style> styles;

    protected Mold(String id) {
        this.id = id;
        properties = new LinkedHashMap<>();
        styles = new LinkedList<>();
    }

    protected Mold() {
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addProperties(Map<String, String> values) {
        if (values != null) properties.putAll(values);
    }

    public void addProperty(String argKey, String value) {
        if (argKey != null && value != null) properties.put(argKey, value);
    }

    public boolean containsPropertyKeys(String... argKeys) {
        for (String argKey : argKeys) if (!properties.containsKey(argKey)) return false;
        return true;
    }

    public void setDefaultStyleTypeIfAbsent(Style defaultStyle) {
        if (styles.stream().noneMatch(style -> style.getType() == defaultStyle.getType())) {
            styles.add(defaultStyle);
        }
    }

    public boolean noProperties() {
        return properties.isEmpty();
    }

    public Map<String, String> getProperties() {
        return new LinkedHashMap<>(properties);
    }

    public String getProperty(String id) {
        return properties.get(id);
    }

    public void addStyle(Style style) {
        if (style != null) styles.add(style);
    }

    public Style getStyle(Style.Keyword keyword) {
        return styles.stream().filter(style -> style.getType() == keyword).findAny().orElseThrow();
    }

    public List<Style> getStyles() {
        return List.copyOf(styles);
    }
}

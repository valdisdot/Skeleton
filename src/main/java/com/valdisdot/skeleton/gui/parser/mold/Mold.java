package com.valdisdot.skeleton.gui.parser.mold;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Abstract parent class for 'mold' -  the central logic unit in parsing the UI. For a single element (label, text field, check box etc.) it is an ElementMold, for a panel - PanelMold.
 * Each mold contains its own properties and information for about the UI.
 */
public abstract class Mold implements Identifiable {
    protected String id;
    protected String title;
    protected Map<String, String> properties;
    protected List<Style> styles;

    /**
     * Instantiates a new Mold with ID.
     *
     * @param id the id
     */
    protected Mold(String id) {
        this.id = id;
        properties = new LinkedHashMap<>();
        styles = new LinkedList<>();
    }

    /**
     * Instantiates an empty Mold.
     */
    protected Mold() {
    }

    /**{@inheritDoc}*/
    @Override
    public String getId() {
        return id;
    }

    /**
     * Return a title of this mold. Nullable.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a title of this mold.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Add properties for this mold.
     *
     * @param values the values
     */
    public void addProperties(Map<String, String> values) {
        if (values != null) properties.putAll(values);
    }

    /**
     * Add a single property to this mold.
     *
     * @param key the key
     * @param value  the value
     */
    public void addProperty(String key, String value) {
        if (key != null && value != null) properties.put(key, value);
    }

    /**
     * Check if the property map contains the keys.
     *
     * @param keys the keys
     * @return result
     */
    public boolean containsPropertyKeys(String... keys) {
        for (String key : keys) if (!properties.containsKey(key)) return false;
        return true;
    }

    /**
     * Sets default style type fot this mold if absent.
     *
     * @param defaultStyle the default style
     */
    public void setDefaultStyleTypeIfAbsent(Style defaultStyle) {
        if (styles.stream().noneMatch(style -> style.getType() == defaultStyle.getType())) {
            styles.add(defaultStyle);
        }
    }

    /**
     * Check if there are no properties in the properties map.
     *
     * @return result
     */
    public boolean noProperties() {
        return properties.isEmpty();
    }

    /**
     * Returns the copy of the properties of this mold.
     *
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return new LinkedHashMap<>(properties);
    }

    /**
     * Gets a single property. Nullable.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Add a style to this mold.
     *
     * @param style the style
     * @see Style
     */
    public void addStyle(Style style) {
        if (style != null) styles.add(style);
    }

    /**
     * Returns a style by its style keyword.
     * @param keyword the keyword
     * @return the style
     * @see Style.Keyword
     */
    public Style getStyle(Style.Keyword keyword) {
        return styles.stream().filter(style -> style.getType() == keyword).findAny().orElseThrow();
    }

    /**
     * Return a list of all styles of this mold.
     *
     * @return the styles
     * @see Style
     */
    public List<Style> getStyles() {
        return List.copyOf(styles);
    }
}

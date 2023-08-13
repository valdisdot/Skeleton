package com.valdisdot.util.ui.gui.parser.json;

import java.awt.*;
import java.util.List;
import java.util.*;

//data class for common element properties
//if some element needs specified color, it can retrieve it by String key
//class is used for ElementMold building
public class JsonPropertyHolder {
    private final Map<String, Object> objectHolder = new HashMap<>();
    private final Map<Class<?>, List<String>> types = new IdentityHashMap<>();

    //only for current package
    JsonPropertyHolder() {
    }

    public void addColor(String key, int hexColor, Color alt) {
        objectHolder.put(key, hexColor >= 0 ? new Color(hexColor) : alt);
        types.putIfAbsent(Color.class, new LinkedList<>());
        types.get(Color.class).add(key);
    }

    public void addFont(String key, String fontFamily, String fontStyle, int fontSize, Font alt) {
        if (Objects.isNull(fontStyle)) fontStyle = "";
        if (Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(fontFamily) && fontSize > 0) {
            objectHolder.put(
                    key,
                    new Font(
                            fontFamily,
                            fontStyle.toLowerCase().contains("bold") ? Font.BOLD : fontStyle.toLowerCase().contains("italic") ? Font.ITALIC : Font.PLAIN,
                            fontSize));
        } else objectHolder.put(key, alt);
        types.putIfAbsent(Font.class, new LinkedList<>());
        types.get(Font.class).add(key);
    }

    public void addSize(String key, int width, int height) {
        if (width > 0 && height > 0) {
            objectHolder.put(Objects.requireNonNull(key, "Size name property is null"), new Dimension(width, height));
            types.putIfAbsent(Dimension.class, new LinkedList<>());
            types.get(Dimension.class).add(key);
        }
    }

    public <T> Optional<T> getProperty(String key, Class<T> tClass) {
        Objects.requireNonNull(key, "Key is null. ");
        Objects.requireNonNull(tClass, "Value type class is null. ");
        try {
            return Optional.of(tClass.cast(objectHolder.get(key)));
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    //example, original key in the app is: 'myFavouriteColor', app somewhere generates and puts the value into the JsonPropertyHolder 'background_json_parsed_myFavouriteColor'
    //we don't want to know the generated key 'background_json_parsed_myFavouriteColor', we want to fetch some Object by your key 'myFavouriteColor'
    public String getRelevantKey(String originalKey) {
        return objectHolder.keySet().stream().filter(key -> key.contains(originalKey)).findFirst().orElse(null);
    }

    //get type, which represents the key
    public Optional<Class<?>> getTypeForKey(String key) {
        return objectHolder.containsKey(key) ? Optional.of(objectHolder.get(key).getClass()) : Optional.empty();
    }

    //simple containing checking
    public boolean containsKey(String key) {
        return objectHolder.containsKey(key);
    }
}

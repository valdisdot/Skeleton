package com.valdisdot.util.ui.gui.parser.mold;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//data class for common element properties
//if some element needs specified color, it can retrieve it by String key
//class is used for ElementMold building
public class FrameMoldProperties {
    private Map<String, Color> colorMap;
    private Map<String, Font> fontMap;
    private Map<String, Dimension> dimensionMap;

    public FrameMoldProperties(){
        colorMap = new HashMap<>();
        fontMap = new HashMap<>();
        dimensionMap = new HashMap<>();
    }

    public void addColor(String key, int hexColor, Color alt){
        colorMap.put(Objects.requireNonNull(key, "Color name property is null"), hexColor >= 0 ? new Color(hexColor) : alt);
    }

    public void addFont(String key, String fontFamily, String fontStyle, int fontSize, Font alt){
        Objects.requireNonNull(key, "Font name property is null");
        if(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(fontFamily) && fontSize > 0) {
            fontMap.put(
                    key,
                    new Font(
                            fontFamily,
                            fontStyle.toLowerCase().contains("bold") ? Font.BOLD : fontStyle.toLowerCase().contains("italic") ? Font.ITALIC : Font.PLAIN,
                            fontSize));
        } else fontMap.put(key, alt);
    }



    public void addSize(String key, int width, int height, Dimension alt){
        dimensionMap.put(Objects.requireNonNull(key, "Size name property is null"), width > 0 && height > 0 ? new Dimension(width, height) : alt);
    }

    public Color getColor(String key){
        return colorMap.get(key);
    }

    public Font getFont(String key){
        return fontMap.get(key);
    }

    public Dimension getSize(String key){
        return dimensionMap.get(key);
    }
}

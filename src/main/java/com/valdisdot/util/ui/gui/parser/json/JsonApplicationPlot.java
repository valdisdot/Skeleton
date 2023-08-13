package com.valdisdot.util.ui.gui.parser.json;

import java.util.LinkedList;
import java.util.List;

//pojo for json data binging
public class JsonApplicationPlot {
    private String applicationName;
    private PropertiesMap properties = new PropertiesMap();
    private List<FramePlot> frames = new LinkedList<>();

    //only for current package
    JsonApplicationPlot() {
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public PropertiesMap getProperties() {
        return properties;
    }

    public void setProperties(PropertiesMap properties) {
        this.properties = properties;
    }

    public List<FramePlot> getFrames() {
        return frames;
    }

    public void setFrames(List<FramePlot> frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return "JsonApplicationPlot{" +
                "applicationName='" + applicationName + '\'' +
                ", properties=" + properties +
                ", frames=" + frames +
                '}';
    }

    static class PropertiesMap {
        private final List<SizesMap> sizesMap = new LinkedList<>();
        private final List<ColorsMap> colorsMap = new LinkedList<>();
        private final List<FontsMap> fontsMap = new LinkedList<>();

        public List<SizesMap> getSizes() {
            return sizesMap;
        }

        public void setSizes(List<SizesMap> values) {
            sizesMap.addAll(values);
        }

        public List<ColorsMap> getColors() {
            return colorsMap;
        }

        public void setColors(List<ColorsMap> values) {
            colorsMap.addAll(values);
        }

        public List<FontsMap> getFonts() {
            return fontsMap;
        }

        public void setFontsMap(List<FontsMap> values) {
            fontsMap.addAll(values);
        }

        @Override
        public String toString() {
            return "PropertiesMap{" +
                    "sizesMap=" + sizesMap +
                    ", colorsMap=" + colorsMap +
                    ", fontsMap=" + fontsMap +
                    '}';
        }
    }

    static class SizesMap {
        private String name;
        private int width = -1;
        private int height = -1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "SizesMap{" +
                    "name='" + name + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    static class ColorsMap {
        private String type;
        private String name;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "ColorsMap{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    static class FontsMap {
        private String name;
        private String fontName;
        private int fontSize = -1;
        private String fontStyle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFontName() {
            return fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(String fontStyle) {
            this.fontStyle = fontStyle;
        }

        @Override
        public String toString() {
            return "FontsMap{" +
                    "name='" + name + '\'' +
                    ", fontName='" + fontName + '\'' +
                    ", fontSize=" + fontSize +
                    ", fontStyle='" + fontStyle + '\'' +
                    '}';
        }
    }

    static class FramePlot {
        private String name;
        private String title;
        private final List<PanelPlot> panels = new LinkedList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<PanelPlot> getPanels() {
            return panels;
        }

        public void setPanels(List<PanelPlot> panels) {
            this.panels.addAll(panels);
        }

        @Override
        public String toString() {
            return "FramePlot{" +
                    "name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", panels=" + panels +
                    '}';
        }
    }

    static class PanelPlot {
        private boolean isFromTheTopOfFrame = true;
        private final List<String> properties = new LinkedList<>();
        private final List<ElementPlot> elements = new LinkedList<>();

        public List<String> getProperties() {
            return properties;
        }

        public void setProperties(List<String> properties) {
            this.properties.addAll(properties);
        }

        public boolean isFromTheTopOfFrame() {
            return isFromTheTopOfFrame;
        }

        public void setFromTheTopOfFrame(boolean fromTheTopOfFrame) {
            isFromTheTopOfFrame = fromTheTopOfFrame;
        }

        public List<ElementPlot> getElements() {
            return elements;
        }

        public void setElements(List<ElementPlot> elements) {
            this.elements.addAll(elements);
        }

        @Override
        public String toString() {
            return "PanelPlot{" +
                    "properties=" + properties +
                    ", elements=" + elements +
                    '}';
        }
    }

    static class ElementPlot {
        private String type;
        private String name;
        private String title;
        private String laying;
        private final List<String> values = new LinkedList<>();
        private final List<String> properties = new LinkedList<>();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLaying() {
            return laying;
        }

        public void setLaying(String laying) {
            this.laying = laying;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values.addAll(values);
        }

        public List<String> getProperties() {
            return properties;
        }

        public void setProperties(List<String> properties) {
            this.properties.addAll(properties);
        }

        @Override
        public String toString() {
            return "ElementPlot{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", laying='" + laying + '\'' +
                    ", values=" + values +
                    ", properties=" + properties +
                    '}';
        }
    }
}

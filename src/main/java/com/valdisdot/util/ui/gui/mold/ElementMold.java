package com.valdisdot.util.ui.gui.mold;

import java.util.List;
import java.util.Objects;

//data class
public class ElementMold {
    private String type;
    private String title;
    private String name;
    private List<String> values;
    private int width;
    private int height;
    private int backgroundColor;
    private int foregroundColor;
    private String fontName;
    private String fontStyle;
    private int fontSize;

    public ElementMold() {
    }

    public ElementMold(String type, String title, String name, List<String> values, int width, int height, int backgroundColor, int foregroundColor, String fontName, String fontStyle, int fontSize) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.values = values;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
    }

    public String getFirstValue() {
        return Objects.isNull(values) || values.isEmpty() ? "" : values.get(0);
    }

    public String getSecondValue() {
        return Objects.isNull(values) || values.size() < 2 ? "" : values.get(1);
    }

    public String getValueAt(int index) {
        return Objects.isNull(values) || values.size() < index ? "" : values.get(index);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElementMold)) return false;
        ElementMold mold = (ElementMold) o;
        return width == mold.width && height == mold.height && backgroundColor == mold.backgroundColor && foregroundColor == mold.foregroundColor && fontSize == mold.fontSize && Objects.equals(type, mold.type) && Objects.equals(title, mold.title) && Objects.equals(name, mold.name) && Objects.equals(values, mold.values) && Objects.equals(fontName, mold.fontName) && Objects.equals(fontStyle, mold.fontStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, name, values, width, height, backgroundColor, foregroundColor, fontName, fontStyle, fontSize);
    }

    @Override
    public String toString() {
        return "ElementMold{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", values=" + values +
                ", width=" + width +
                ", height=" + height +
                ", backgroundColor=" + backgroundColor +
                ", foregroundColor=" + foregroundColor +
                ", fontName='" + fontName + '\'' +
                ", fontStyle='" + fontStyle + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }
}

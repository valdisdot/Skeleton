package com.valdisdot.skeleton.gui.view.swing;

import com.valdisdot.skeleton.core.Identifiable;
import com.valdisdot.skeleton.gui.parser.mold.Style;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public abstract class JElement implements Identifiable {
    private final Collection<JComponent> internal;
    private JComponent component;

    protected JElement() {
        internal = new LinkedList<>();
    }

    protected JElement(JComponent component) {
        this();
        setComponent(component);
    }

    @Override
    public String getId() {
        return component.getName();
    }

    protected <V extends JComponent> V setComponent(V component) {
        this.component = Objects.requireNonNull(component, "JComponent is null");
        Objects.requireNonNull(component.getName(), "JComponent.getName() is null");
        return component;
    }

    protected <V extends JComponent> V addPart(V component) {
        internal.add(component);
        return component;
    }

    //parse the style and return the result object (color, size, font) for next possible usage
    public Object applyStyle(Style style) {
        Map<Style.Keyword, String> args = style.getValues();
        switch (style.getType()) {
            case SIZE: {
                Dimension dimension = new Dimension(
                        Integer.parseInt(args.get(Style.Keyword.WIDTH)),
                        Integer.parseInt(args.get(Style.Keyword.HEIGHT))
                );
                component.setPreferredSize(dimension);
                component.setMaximumSize(dimension);
                return dimension;
            }

            case BACKGROUND_COLOR: {
                Color color = new Color(Integer.parseInt(args.get(Style.Keyword.COLOR).replaceAll("#", ""), 16));
                component.setBackground(color);
                internal.forEach(i -> i.setBackground(color));
                return color;
            }
            case FOREGROUND_COLOR: {
                Color color = new Color(Integer.parseInt(args.get(Style.Keyword.COLOR).replaceAll("#", ""), 16));
                component.setForeground(color);
                internal.forEach(i -> i.setForeground(color));
                return color;
            }
            case FONT: {
                int fontStyle = args.get(Style.Keyword.FONT_STYLE_KEY).equalsIgnoreCase(Style.Keyword.FONT_STYLE_ITALIC.getValue()) ? Font.ITALIC : args.get(Style.Keyword.FONT_STYLE_KEY).equalsIgnoreCase(Style.Keyword.FONT_STYLE_BOLD.getValue()) ? Font.BOLD : Font.PLAIN;
                Font font = new Font(
                        args.get(Style.Keyword.FONT_NAME),
                        fontStyle,
                        Integer.parseInt(args.get(Style.Keyword.FONT_SIZE))
                );
                component.setFont(font);
                internal.forEach(i -> i.setFont(font));
                return font;
            }
            default:
                return null;
        }
    }

    public Collection<JComponent> getInternal() {
        return internal;
    }

    public JComponent getComponent() {
        return component;
    }
}

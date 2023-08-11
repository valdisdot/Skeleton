package com.valdisdot.util.ui.gui.component;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.Supplier;

//util class, builder for decorate JElements with colors, size and font
//is used for parsing
public class JComponentDecorator<C extends JComponent> implements Supplier<C> {
    private C component;

    public JComponentDecorator(C component) {
        this.component = component;
    }

    public JComponentDecorator(Supplier<C> componentSupplier) {
        this.component = Objects.requireNonNull(componentSupplier).get();
    }

    public JComponentDecorator<C> preferredSize(Dimension dimension) {
        component.setPreferredSize(dimension);
        return this;
    }

    public JComponentDecorator<C> preferredSize(int width, int height) {
        if (width > 0 && height > 0) component.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public JComponentDecorator<C> backgroundColor(Color color) {
        component.setBackground(color);
        return this;
    }

    public JComponentDecorator<C> background(int hexColor) {
        if (hexColor >= 0) component.setBackground(new Color(hexColor));
        return this;
    }

    public JComponentDecorator<C> foreground(int hexColor) {
        if (hexColor >= 0) component.setForeground(new Color(hexColor));
        return this;
    }

    public JComponentDecorator<C> font(Font font) {
        component.setFont(font);
        return this;
    }

    public JComponentDecorator<C> font(String fontName, String fontStyle, int fontSize) {
        if (Objects.nonNull(fontName) && Objects.nonNull(fontStyle) && !fontName.isBlank() && fontSize > 0) {
            fontStyle = fontStyle.toLowerCase();
            component.setFont(new Font(
                    fontName,
                    fontStyle.contains("bold") ? Font.BOLD : fontStyle.contains("italic") ? Font.ITALIC : Font.PLAIN,
                    fontSize
            ));
        }
        return this;
    }

    @Override
    public C get() {
        try {
            return component;
        } finally {
            component = null;
        }
    }
}

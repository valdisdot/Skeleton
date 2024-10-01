package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.gui.parser.mold.Style;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The implementation of a content button element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class ContentButton extends JSinglePresentableUnit implements DataUnit<String> {
    private final JButton button;
    private final String selected;
    private final String deselected;
    private boolean wasPressed = false;
    private Color pressedColor;
    private Color defaultColor;

    /**
     * Instantiates a new content button.
     *
     * @param id          an id, not null
     * @param title       a title, nullable
     * @param preselected flag indicates an initial state of the checkbox
     * @param selected    a selected value, nullable
     * @param deselected  a deselected value, nullable
     */
    public ContentButton(String id, String title, boolean preselected, String selected, String deselected) {
        this.selected = Objects.requireNonNullElse(selected, "true");
        this.deselected = Objects.requireNonNullElse(deselected, "false");

        JButton button = new JButton(title == null ? "" : title);
        button.setName(id);
        button.setMargin(new Insets(5, 5, 5, 5));
        button.setFocusable(false);
        defaultColor = button.getBackground();
        pressedColor = new Color(isDark(defaultColor.getRGB()) ? defaultColor.getRGB() + 128 : defaultColor.getRGB() - 64);
        button.addActionListener(l -> {
            wasPressed = !wasPressed;
            if (wasPressed) button.setBackground(pressedColor);
            else button.setBackground(defaultColor);
        });
        this.button = setComponent(button);
        if (preselected) button.doClick();
    }

    /**
     * Instantiates a new content button.
     *
     * @param id    an id, not null
     * @param title a title, nullable
     */
    public ContentButton(String id, String title) {
        this(id, title, false, null, null);
    }

    /**
     * Instantiates a new Content button.
     *
     * @param id an id
     */
    public ContentButton(String id) {
        this(id, null, false, null, null);
    }

    private boolean isDark(int hexColor) {
        int r = (hexColor >> 16) & 0xff;
        int g = (hexColor >> 8) & 0xff;
        int b = (hexColor) & 0xff;
        int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b); //ITU-R BT.709
        return luma < 128;
    }

    /**{@inheritDoc}*/
    @Override
    public Object applyStyle(Style style) {
        try {
            return super.applyStyle(style);
        } finally {
            defaultColor = getComponent().getBackground();
            pressedColor = new Color(isDark(defaultColor.getRGB()) ? defaultColor.getRGB() + 128 : defaultColor.getRGB() - 64);
        }
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        return wasPressed ? selected : deselected;
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        if ((selected.equals(data) && !wasPressed) || (deselected.equals(data) && wasPressed)) button.doClick();
        //else do nothing
    }

    /**{@inheritDoc}*/
    @Override
    public void resetData() {
        if (wasPressed) button.doClick();
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) button.setText(presentation);
    }
}

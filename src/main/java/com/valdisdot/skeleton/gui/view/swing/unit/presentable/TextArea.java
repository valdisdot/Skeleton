package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The implementation of a text area element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class TextArea extends JSinglePresentableUnit implements DataUnit<String> {
    private final JTextArea textArea;
    private String defaultText;

    /**
     * Instantiates a new text area.
     *
     * @param id          an id, not null
     * @param defaultText default text, nullable
     */
    public TextArea(String id, String defaultText) {
        this.defaultText = Objects.requireNonNullElse(defaultText, "");
        JTextArea textArea = new JTextArea(this.defaultText);
        textArea.setText(this.defaultText);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.textArea = textArea;
        JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) {
            @Override
            public void setFont(Font font) {
                super.setFont(font);
                textArea.setFont(font);
            }

            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                textArea.setForeground(fg);
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(bg);
                textArea.setBackground(bg);
            }
        };
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setName(id);
        setComponent(scrollPane);
    }

    /**
     * Instantiates a new text area.
     *
     * @param id the id
     */
    public TextArea(String id) {
        this(id, null);
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        return textArea.getText();
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        textArea.setText(data == null ? "" : data);
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        textArea.setText(defaultText);
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) {
            this.defaultText = presentation;
            textArea.setText(presentation);
        }
    }
}

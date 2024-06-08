package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TextArea extends JSinglePresentableUnit implements DataUnit<String> {
    private final JTextArea textArea;
    private String defaultText;

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

    public TextArea(String id) {
        this(id, null);
    }

    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), textArea.getText());
    }

    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) textArea.setText(data.fetchFirst());
    }

    @Override
    public void reset() {
        textArea.setText(defaultText);
    }

    @Override
    public void setPresentation(String presentation) {
        this.defaultText = presentation;
        textArea.setText(presentation);
    }
}

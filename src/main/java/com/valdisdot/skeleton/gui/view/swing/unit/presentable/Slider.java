package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.core.Pair;
import com.valdisdot.skeleton.gui.parser.mold.Style;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;

import javax.swing.*;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Consumer;

/**
 * The implementation of a slider element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class Slider extends JMultiPresentableUnit implements DataUnit<String> {
    private final JSlider slider;

    /**
     * Instantiates a new slider.
     *
     * @param id         an id, not null
     * @param view       a view, nullable
     * @param isVertical flag indicates an orientation of the slider
     * @see Pair
     */
    public Slider(String id, Collection<Pair> view, boolean isVertical) {
        JSlider slider = new JSlider(isVertical ? JSlider.VERTICAL : JSlider.HORIZONTAL);
        slider.setName(id);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        this.slider = setComponent(slider);
        replacePresentations(view);
    }

    /**
     * Instantiates a new slider.
     *
     * @param id   an id, not null
     * @param view a view, nullable
     * @see Pair
     */
    public Slider(String id, Collection<Pair> view) {
        this(id, view, false);
    }

    /**
     * Instantiates a new slider.
     *
     * @param id an id, not null
     */
    public Slider(String id) {
        this(id, null, false);
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        return getCurrentView().get(slider.getValue()).getId();
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        int i = 0;
        for (Pair pair : getCurrentView()) {
            if (pair.getId().equals(data)) {
                slider.setValue(i);
                return;
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void resetData() {
        slider.setValue(0);
    }

    /**{@inheritDoc}*/
    @Override
    public void apply(Consumer<JComponent> method) {
        super.apply(method);
        updateView();
    }

    /**{@inheritDoc}*/
    @Override
    public Object applyStyle(Style style) {
        try {
            return super.applyStyle(style);
        } finally {
            updateView();
        }
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        slider.setLabelTable(null);
        List<Pair> view = getCurrentView();
        if (!view.isEmpty()) {
            int index = 0;
            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            JLabel label;
            for (Pair pair : view) {
                label = new JLabel(pair.toString());
                label.setForeground(slider.getForeground());
                label.setFont(slider.getFont());
                labels.put(index++, label);
            }
            slider.setMaximum(0);
            slider.setMaximum(view.size() - 1);
            slider.setLabelTable(labels);
        }
    }
}

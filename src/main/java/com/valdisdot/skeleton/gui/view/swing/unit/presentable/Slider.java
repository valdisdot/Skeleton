package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
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
     * @see PresentablePair
     */
    public Slider(String id, Collection<PresentablePair> view, boolean isVertical) {
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
     * @see PresentablePair
     */
    public Slider(String id, Collection<PresentablePair> view) {
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
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), getCurrentView().get(slider.getValue()).toString());
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) {
            String value = data.fetchFirst();
            int i = 0;
            for (PresentablePair pair : getCurrentView()) {
                if (pair.getId().equals(value)) {
                    slider.setValue(i);
                    return;
                }
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
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
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            int index = 0;
            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            JLabel label;
            for (PresentablePair pair : view) {
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

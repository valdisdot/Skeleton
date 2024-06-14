package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The implementation of a multi combo box element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class MultiComboBox extends JMultiPresentableUnit implements DataUnit<String> {
    private final Function<Collection<String>, String> toLineFunction;
    private final JList<PresentablePair> list;

    /**
     * Instantiates a new multi combo box.
     * @param id             an id, not null
     * @param view           a view, nullable
     * @param toLineFunction a collection-to-line function, nullable
     * @see PresentablePair
     */
    public MultiComboBox(String id, Collection<PresentablePair> view, Function<Collection<String>, String> toLineFunction) {
        JList<PresentablePair> list = new JList<>();
        list.setName(id);
        this.list = list;
        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) {
            @Override
            public void setFont(Font font) {
                super.setFont(font);
                list.setFont(font);
            }

            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                list.setForeground(fg);
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(bg);
                list.setBackground(bg);
            }
        };
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setName(id);
        setComponent(scrollPane);
        this.toLineFunction = toLineFunction == null ? collection -> collection.stream().collect(Collectors.joining(",", "\"", "\"")) : toLineFunction;
        replacePresentations(view);
    }

    /**
     * Instantiates a new multi combo box.
     * @param id   an id, not null
     * @param view a view, nullable
     * @see PresentablePair
     */
    public MultiComboBox(String id, Collection<PresentablePair> view) {
        this(id, view, null);
    }

    /**
     * Instantiates a new multi combo box.
     *
     * @param id             an id, not null
     * @param toLineFunction a collection-to-line function, nullable
     */
    public MultiComboBox(String id, Function<Collection<String>, String> toLineFunction) {
        this(id, null, toLineFunction);
    }

    /**
     * Instantiates a new multi combo box.
     *
     * @param id an id, not null
     */
    public MultiComboBox(String id) {
        this(id, null, null);
    }

    /**{@inheritDoc}*/
    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), toLineFunction.apply(list.getSelectedValuesList().stream().map(PresentablePair::getId).collect(Collectors.toList())));
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        List<String> values = data.fetchList();
        if (!values.isEmpty()) {
            int i = 0;
            for (PresentablePair pair : getCurrentView()) {
                if (values.contains(pair.getId())) list.setSelectedIndex(i);
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        list.clearSelection();
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        list.removeAll();
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            DefaultListModel<PresentablePair> model = new DefaultListModel<>();
            model.addAll(view);
            list.setModel(model);
        }
    }
}

package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
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
    private final JList<Pair> list;

    /**
     * Instantiates a new multi combo box.
     * @param id             an id, not null
     * @param view           a view, nullable
     * @param toLineFunction a collection-to-line function, nullable
     * @see Pair
     */
    public MultiComboBox(String id, Collection<Pair> view, Function<Collection<String>, String> toLineFunction) {
        JList<Pair> list = new JList<>();
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
     * @see Pair
     */
    public MultiComboBox(String id, Collection<Pair> view) {
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
    public String getData() {
        return toLineFunction.apply(list.getSelectedValuesList().stream().map(Pair::getId).collect(Collectors.toList()));
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
            int i = 0;
            for (Pair pair : getCurrentView()) {
                if (pair.getId().equals(data)) {
                    list.setSelectedIndex(i);
                    return;
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
        List<Pair> view = getCurrentView();
        if (!view.isEmpty()) {
            DefaultListModel<Pair> model = new DefaultListModel<>();
            model.addAll(view);
            list.setModel(model);
        }
    }
}

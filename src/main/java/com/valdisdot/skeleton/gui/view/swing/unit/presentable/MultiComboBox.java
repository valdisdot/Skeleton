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

public class MultiComboBox extends JMultiPresentableUnit implements DataUnit<String> {
    private final Function<Collection<String>, String> toLineFunction;
    private final JList<PresentablePair> list;

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
        if (view != null && !view.isEmpty()) replacePresentations(view);
    }

    public MultiComboBox(String id, Collection<PresentablePair> view) {
        this(id, view, null);
    }

    public MultiComboBox(String id, Function<Collection<String>, String> toLineFunction) {
        this(id, null, toLineFunction);
    }

    public MultiComboBox(String id) {
        this(id, null, null);
    }


    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), toLineFunction.apply(list.getSelectedValuesList().stream().map(PresentablePair::getId).collect(Collectors.toList())));
    }

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

    @Override
    public void reset() {
        list.clearSelection();
    }

    @Override
    protected void updateView() {
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            list.removeAll();
            DefaultListModel<PresentablePair> model = new DefaultListModel<>();
            model.addAll(view);
            list.setModel(model);
        }
    }
}

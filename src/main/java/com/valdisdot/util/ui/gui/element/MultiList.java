package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;
import java.util.List;

public class MultiList<D> extends JElement<List<D>> {
    public MultiList(String name, JList<D> list) {
        list.setName(Objects.requireNonNull(name));
        completeInitialization(list, new DataCell<>(
                list::getSelectedValuesList,
                (val) -> list.clearSelection()
        ));
    }

    public MultiList(String name, Collection<D> items) {
        this(name, new JList<>(new Vector<>(Objects.requireNonNull(items))));
    }

    @Override
    protected boolean pleaseAcceptThatYouHaveDone() {
        return true;
    }
}

package com.valdisdot.util.ui.gui.component;

import javax.swing.*;
import java.util.*;
import java.util.function.Supplier;

public class Panel implements Supplier<JComponent> {
    private List<Supplier<JComponent>> queue;

    public Panel(){
        queue = new LinkedList<>();
    }

    public void add(Supplier<JComponent> componentSupplier){
        queue.add(Objects.requireNonNull(componentSupplier));
    }

    public void add(Collection<Supplier<JComponent>> collection){
        queue.addAll(Objects.requireNonNull(collection));
    }

    public void addFirst(Supplier<JComponent> componentSupplier){
        queue.add(0, Objects.requireNonNull(componentSupplier));
    }

    @Override
    public JComponent get() {
        return null;
    }
    //later
}

package com.valdisdot.util.ui.gui.component;

import com.valdisdot.util.ui.gui.util.ContentPanel;
import com.valdisdot.util.ui.gui.util.LayoutManager;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class Panel implements Supplier<JComponent> {
    private final JPanel contentPanel;

    public Panel(JPanel contentPanel) {
        this.contentPanel = Objects.requireNonNull(contentPanel);
    }

    public Panel() {
        this(new ContentPanel());
    }

    public Panel(LayoutManager.Order order, int indentX, int indentY, boolean isInternalPanel) {
        this(new ContentPanel(Objects.requireNonNull(order), indentX, indentY, isInternalPanel));
    }

    public Panel(Collection<? extends Supplier<JComponent>> componentSuppliers, JPanel contentPanel) {
        this.contentPanel = Objects.requireNonNull(contentPanel);
        this.add(componentSuppliers);
    }

    public Panel(Collection<? extends Supplier<JComponent>> componentSuppliers) {
        this(
                componentSuppliers,
                new ContentPanel()
        );
    }

    public Panel(Collection<? extends Supplier<JComponent>> componentSuppliers, LayoutManager.Order order, int indentX, int indentY, boolean isInternalPanel) {
        this(
                componentSuppliers,
                new ContentPanel(Objects.requireNonNull(order), indentX, indentY, isInternalPanel)
        );
    }

    public void add(Supplier<JComponent> componentSupplier) {
        contentPanel.add(Objects.requireNonNull(componentSupplier).get());
    }

    public void add(Collection<? extends Supplier<JComponent>> collection) {
        Objects.requireNonNull(collection).forEach(componentSupplier -> contentPanel.add(componentSupplier.get()));
    }

    @Override
    public JComponent get() {
        return contentPanel;
    }
}

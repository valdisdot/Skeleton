package com.valdisdot.skeleton.gui.view.swing.unit;

import com.valdisdot.skeleton.core.view.PresentableUnit;
import com.valdisdot.skeleton.gui.view.swing.JElement;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * The helper abstract parent class for code reducing. Contains common for all Swing implementations methods and default implementations of some PresentableUnit methods.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public abstract class JPresentableUnit extends JElement implements PresentableUnit<JComponent> {
    protected JPresentableUnit(JComponent component) {
        super(component);
        setComponent(component);
    }

    protected JPresentableUnit() {
        super();
    }

    /**{@inheritDoc}*/
    @Override
    public void apply(Consumer<JComponent> method) {
        method.accept(getComponent());
        getInternal().forEach(method);
    }
}

package com.valdisdot.skeleton.gui.view.swing.unit;

import com.valdisdot.skeleton.core.Pair;

import javax.swing.*;
import java.util.Collection;

/**
 * The abstract implementation for elements where a view presentation is singular and does not contain inner elements (like label, button etc.).
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public abstract class JSinglePresentableUnit extends JPresentableUnit {
    protected JSinglePresentableUnit() {
    }

    protected JSinglePresentableUnit(JComponent component) {
        super(component);
    }

    /**{@inheritDoc}*/
    @Override
    public void updatePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()) presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }

    /**{@inheritDoc}*/
    @Override
    public void completePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()) presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }

    /**{@inheritDoc}*/
    @Override
    public void replacePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()) presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }
}

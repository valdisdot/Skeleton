package com.valdisdot.skeleton.gui.view.swing.unit;

import com.valdisdot.skeleton.core.view.PresentablePair;

import javax.swing.*;
import java.util.Collection;

public abstract class JSinglePresentableUnit extends JPresentableUnit {
    protected JSinglePresentableUnit() {
    }

    protected JSinglePresentableUnit(JComponent component) {
        super(component);
    }

    @Override
    public void updatePresentations(Collection<PresentablePair> presentations) {
        presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }

    @Override
    public void completePresentations(Collection<PresentablePair> presentations) {
        presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }

    @Override
    public void replacePresentations(Collection<PresentablePair> presentations) {
        presentations.stream().findFirst().ifPresent(entry -> setPresentation(entry.toString()));
    }
}

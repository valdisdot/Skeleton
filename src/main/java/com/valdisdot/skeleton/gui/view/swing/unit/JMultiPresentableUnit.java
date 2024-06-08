package com.valdisdot.skeleton.gui.view.swing.unit;

import com.valdisdot.skeleton.core.view.PresentablePair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class JMultiPresentableUnit extends JPresentableUnit {
    private final List<PresentablePair> view;

    protected JMultiPresentableUnit() {
        this.view = new ArrayList<>(5);
    }

    @Override
    public void completePresentations(Collection<PresentablePair> presentations) {
        for (PresentablePair pair : presentations) {
            if (view.contains(pair)) view.get(view.indexOf(pair)).setPresentation(pair.toString());
            else view.add(pair);
        }
        updateView();
    }

    @Override
    public void replacePresentations(Collection<PresentablePair> presentations) {
        view.clear();
        view.addAll(presentations);
        updateView();
    }

    @Override
    public void setPresentation(String presentation) {
        //do nothing for a multivalued presentation
    }

    @Override
    public void updatePresentations(Collection<PresentablePair> presentations) {
        for (PresentablePair pair : presentations) {
            if (view.contains(pair)) view.get(view.indexOf(pair)).setPresentation(pair.toString());
        }
        updateView();
    }

    protected List<PresentablePair> getCurrentView() {
        return view;
    }

    protected abstract void updateView();
}

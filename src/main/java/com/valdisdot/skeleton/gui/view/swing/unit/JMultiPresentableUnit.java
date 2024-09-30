package com.valdisdot.skeleton.gui.view.swing.unit;

import com.valdisdot.skeleton.core.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The abstract implementation for elements where a view presentation may contain inner elements (like combo box, radio box etc.).
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public abstract class JMultiPresentableUnit extends JPresentableUnit {
    private final List<Pair> view;

    protected JMultiPresentableUnit() {
        this.view = new ArrayList<>(5);
    }

    /**{@inheritDoc}*/
    @Override
    public void completePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()) {
            for (Pair pair : presentations) {
                if (view.contains(pair)) view.get(view.indexOf(pair)).setPresentation(pair.toString());
                else view.add(pair);
            }
            updateView();
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void replacePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()){
            view.clear();
            view.addAll(presentations);
            updateView();
        }
    }

    /**
     * @apiNote Adapter method, does nothing in the implementation.
     */
    @Override
    public void setPresentation(String presentation) {
        //do nothing for a multivalued presentation
    }

    /**{@inheritDoc}*/
    @Override
    public void updatePresentations(Collection<Pair> presentations) {
        if(presentations != null && !presentations.isEmpty()) {
            for (Pair pair : presentations) {
                if (view.contains(pair)) view.get(view.indexOf(pair)).setPresentation(pair.toString());
            }
            updateView();
        }
    }

    /**
     * @return the current presentation, internal elements view.
     */
    protected List<Pair> getCurrentView() {
        return view;
    }

    /**
     * Updates the view, method will be called after all presentation modifications.
     */
    protected abstract void updateView();
}

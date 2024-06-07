package com.valdisdot.skeleton.core.view;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * The interface represents an API for update view of component and its pairs of data-presentation and view-presentation.
 *
 * @param <ComponentViewType> the view type of the component.
 * @author Vladyslav Tymchenko
 * @apiNote Data-presentation means business logic name like ID, view-presentation can be a label, a title etc.
 * @since 1.0
 */
public interface PresentableUnit<ComponentViewType> extends Identifiable {
    /**
     * Sets just a one presentation.
     *
     * @param presentation
     */
    void setPresentation(String presentation);

    /**
     * Updates presentations, which are already exists in the PresentableUnit by ID.
     *
     * @param presentations
     */
    void updatePresentations(Collection<PresentablePair> presentations);

    /**
     * Updates presentations and adds the absent ones from the presentations object.
     *
     * @param presentations
     */
    void completePresentations(Collection<PresentablePair> presentations);

    /**
     * Removes all presentations and adds all from the presentations.
     *
     * @param presentations
     */
    void replacePresentations(Collection<PresentablePair> presentations);

    /**
     * Inverted getter. Allows to apply a custom function to the {@code <ComponentViewType>} without returning the {@code <ComponentViewType>}.
     *
     * @param method
     */
    void apply(Consumer<ComponentViewType> method);
}

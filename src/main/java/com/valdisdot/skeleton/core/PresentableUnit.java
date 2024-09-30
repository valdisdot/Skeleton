package com.valdisdot.skeleton.core;

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
     * @see Pair
     */
    void updatePresentations(Collection<Pair> presentations);

    /**
     * Updates presentations and adds the absent ones from the presentations object.
     *
     * @param presentations
     * @see Pair
     */
    void completePresentations(Collection<Pair> presentations);

    /**
     * Removes all presentations and adds all from the presentations.
     *
     * @param presentations
     * @see Pair
     */
    void replacePresentations(Collection<Pair> presentations);

    /**
     * Inverted getter. Allows to apply a custom function to the {@code <ComponentViewType>} without returning the {@code <ComponentViewType>}.
     *
     * @param method
     */
    void apply(Consumer<ComponentViewType> method);


}

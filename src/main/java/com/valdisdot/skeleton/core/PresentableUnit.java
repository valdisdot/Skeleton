package com.valdisdot.skeleton.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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


    /**
     * The class is a wrapper for data-presentation (like ID) and its view-presentation (like label or title) and has similar purpose to the Map.Entry class but with meaningful method names.
     *
     * @author Vladyslav Tymchenko
     * @since 1.0
     */
    class Pair implements Identifiable {
        private final String identifier;
        private String presentation;

        public Pair(String identifier, String presentation) {
            this.identifier = identifier;
            this.presentation = presentation;
        }

        /**
         * Converts a map of strings into the list of presentable pairs.
         *
         * @param values - a map.
         * @return list of pairs.
         */
        public static List<Pair> fromMap(Map<String, String> values) {
            return values.entrySet().stream().map(entry -> new Pair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getId() {
            return identifier;
        }

        /**
         * Sets the current value of its representation.
         *
         * @param presentation
         */
        public void setPresentation(String presentation) {
            this.presentation = presentation;
        }

        /**
         * Gets a presentation which will be used just internally (with GUI, for example), therefore it must be well-known for any libraries which work with Strings.
         *
         * @return presentation.
         */
        @Override
        public String toString() {
            return presentation;
        }
    }
}

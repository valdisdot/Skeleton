package com.valdisdot.skeleton.core.view;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class is a wrapper for data-presentation (like ID) and its view-presentation (like label or title) and has similar purpose to the Map.Entry class but with meaningful method names.
 *
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public class PresentablePair implements Identifiable {
    private final String identifier;
    private String presentation;

    public PresentablePair(String identifier, String presentation) {
        this.identifier = identifier;
        this.presentation = presentation;
    }

    /**
     * Converts a map of strings into the list of presentable pairs.
     *
     * @param values - a map.
     * @return list of pairs.
     */
    public static List<PresentablePair> fromMap(Map<String, String> values) {
        return values.entrySet().stream().map(entry -> new PresentablePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
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

package com.valdisdot.skeleton.core;


/**
 * The mark interface of the identifiable element (with an ID).
 *
 * @author Vladyslav Tymchenko
 * @apiNote All essential library's components are identifiable.
 * @since 1.0
 */
public interface Identifiable extends Comparable<Identifiable> {
    /**
     * @return an identifier of the element.
     */
    String getId();

    /**
     * @param other an Identifiable to be compared.
     * @return a comparing integer of IDs.
     */
    @Override
    default int compareTo(Identifiable other) {
        return getId().compareTo(other.getId());
    }
}
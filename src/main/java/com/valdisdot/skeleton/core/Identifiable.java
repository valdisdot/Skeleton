package com.valdisdot.skeleton.core;

public interface Identifiable extends Comparable<Identifiable> {
    String getId();

    @Override
    default int compareTo(Identifiable other) {
        return getId().compareTo(other.getId());
    }
}

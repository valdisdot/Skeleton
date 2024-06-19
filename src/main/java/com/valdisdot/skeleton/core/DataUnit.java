package com.valdisdot.skeleton.core;

/**
 * The class is a container to data states in the application.
 *
 * @param <Data> type of data exchange between view components and business logic.
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface DataUnit<Data> extends Identifiable {
    /**
     * @return the current data state of an element
     */
    Data getData();

    /**
     * @param data a new data state of an element
     */
    void setData(Data data);

    /**
     * @param data a new data state of an element
     * @return the current data state of an element
     */
    default Data exchange(Data data) {
        Data res = getData();
        setData(data);
        return res;
    }

    /**
     * Must reset the current data state of an element for the default state.
     */
    void reset();
}

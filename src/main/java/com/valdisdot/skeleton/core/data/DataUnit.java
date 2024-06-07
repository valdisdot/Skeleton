package com.valdisdot.skeleton.core.data;

import com.valdisdot.skeleton.core.Identifiable;

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
    DataBean<Data> getBean();

    /**
     * @param data a new data state of an element
     */
    void setBean(DataBean<Data> data);

    /**
     * @param data a new data state of an element
     * @return the current data state of an element
     */
    default DataBean<Data> exchange(DataBean<Data> data) {
        DataBean<Data> res = getBean();
        if (data.isPresent()) setBean(data);
        return res;
    }

    /**
     * Must reset the current data state of an element for the default state.
     */
    void reset();
}

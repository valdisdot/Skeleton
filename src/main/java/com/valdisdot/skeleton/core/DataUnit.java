package com.valdisdot.skeleton.core;

/**
 * The class is a container to data states in the application.
 *
 * @param <DataType> type of data exchange between view components and business logic.
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface DataUnit<DataType> extends Identifiable {
    /**
     * @return the current data state of an element
     */
    DataType getData();

    /**
     * @param dataType a new data state of an element
     */
    void setData(DataType dataType);

    /**
     * @param dataType a new data state of an element
     * @return the current data state of an element
     */
    default DataType exchangeData(DataType dataType) {
        DataType res = getData();
        setData(dataType);
        return res;
    }

    /**
     * Must reset the current data state of an element for the default state.
     */
    void resetData();
}

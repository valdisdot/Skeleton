package com.valdisdot.skeleton.data.element;

import com.valdisdot.skeleton.data.DataCell;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
The class is a holder for a map of logical name pairs ["elem_name", DataCell<?> dc]. This class helps operate with data on-demand.
For example, we have named some JLabel LABEL with logical name (from json, xml ect.) "temperature_display" and we have the DataCell from this JLabel (temperatureDisplayDC = new DataCell<>(LABEL::getText, LABEL::setText)).
Next, we add the pair name-data cell to the DataCellGroup<String> => dcg.putDataCell("temperature_display", temperatureDisplayDC).
Then, we can read/set data by dcg.setDataForCell("temperature_display", "120 K") on demand.
The holder can be placed anywhere (static context, DI, inside some data supplier), we are not bound with a specific place (Components can can be at JFrame A, calling button can be placed at JFrame D)
The main purpose of the class (and the whole library) - to divide application logic and view (based on old swing libraries). Also, to create a base for future view-parsing libraries (from JSON to Java Swing app).
*/

public class DataCellGroup<D> {
    private final Map<String, DataCell<D>> dataCellMap;

    public DataCellGroup() {
        dataCellMap = new HashMap<>();
    }

    public DataCellGroup(Map<String, DataCell<D>> dataCellMap) {
        this();
        this.dataCellMap.putAll(Objects.requireNonNull(dataCellMap));
    }

    //pair example: ["login", new DataCell<>(jTextArea::getText, jTextArea::setText)]
    public void put(String dataCellName, DataCell<D> dataCell) {
        dataCellMap.put(Objects.requireNonNull(dataCellName), Objects.requireNonNull(dataCell));
    }

    //for cases, where you placed control button (and you don't want it to contain data) as content button
    public DataCell<D> remove(String dataCellName) {
        return dataCellMap.remove(dataCellName);
    }

    public Set<String> getDataCellNames() {
        return dataCellMap.keySet();
    }

    //get raw data state
    public D getDataForCell(String dataCellName) {
        return dataCellMap.get(dataCellName).getData();
    }

    public Map<String, D> getAllData() {
        return dataCellMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getData()));
    }

    //get data state with converting
    //BiFunction<CELL-NAME, CELL-VALUE, CONVERTING-TYPE>
    public <C> C getDataForCell(String dataCellName, BiFunction<String, D, C> convertingFunction) {
        return Objects.requireNonNull(convertingFunction, "Converting bi-function is null")
                .apply(dataCellName, dataCellMap.get(dataCellName).getData());
    }

    public <C> C getAllData(Function<Map<String, D>, C> converingFunction) {
        return Objects.requireNonNull(converingFunction, "Converting function is null")
                .apply(getAllData());
    }

    //set data state
    public void setDataForCell(String dataCellName, D data) {
        dataCellMap.get(dataCellName).setData(data);
    }

    //for bulk reset
    public void setDataForAllCells(D data) {
        dataCellMap.values().forEach(dataCell -> dataCell.setData(data));
    }
}

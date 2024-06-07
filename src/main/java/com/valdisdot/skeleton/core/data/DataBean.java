package com.valdisdot.skeleton.core.data;

import com.valdisdot.skeleton.core.Identifiable;

import java.util.*;

/**
 * The class represents a universal data transfer object, based on common data structures.
 * It operates with a single value, list of values and values aggregation as a map.
 *
 * @param <DataType> a type of its data.
 * @author Vladyslav Tymchenko
 * @apiNote Note that if your intention is to use this class with aggregations map, {@code <DataType>} data type class must override {@code hashcode()} and {@code equals()}.
 * @since 1.0
 */
public final class DataBean<DataType> implements Identifiable {
    private String id;
    private DataType first;
    private List<DataType> list;
    private Map<DataType, Integer> map;

    /**
     * Constructor for a single-valued DTO.
     *
     * @param id            id of its DTO.
     * @param singleElement data of its DTO.
     */
    public DataBean(String id, DataType singleElement) {
        this.id = Objects.requireNonNull(id);
        this.first = singleElement;
    }

    /**
     * Constructor for a multi-valued DTO.
     *
     * @param id   id of its DTO.
     * @param list data of its DTO.
     */
    public DataBean(String id, List<DataType> list) {
        this.id = Objects.requireNonNull(id);
        this.list = list;
    }

    /**
     * Constructor for an aggregation-valued DTO.
     *
     * @param id  id of its DTO.
     * @param map data of its DTO.
     */
    public DataBean(String id, Map<DataType, Integer> map) {
        this.id = Objects.requireNonNull(id);
        this.map = map;
    }

    //for internal only
    private DataBean() {
    }

    /**
     * @return an empty DataBean of type {@code <T>}
     */
    public static <T> DataBean<T> empty() {
        return new DataBean<T>();
    }

    /**
     * @return check if the value is neither a list and aggregated map.
     */
    public boolean isSingleValued() {
        return first != null;
    }

    /**
     * @return check if the value is neither a single-value and aggregated map.
     */
    public boolean isList() {
        return list != null;
    }

    /**
     * @return check if the value is neither a list and single-value.
     */
    public boolean isAggregated() {
        return map != null;
    }

    /**
     * @return check if the value is present and accessible in any ways.
     */
    public boolean isPresent() {
        return id != null && (first != null || (list != null && !list.isEmpty()) || (map != null && !map.isEmpty()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Return a value "as is".
     *
     * @return a data as a single value, nullable.
     */
    public DataType getFirst() {
        try {
            return first;
        } finally {
            release();
        }
    }

    /**
     * Expensive version of {@code getFirst()}, which try to get a single value from a list or a map, if a single value is absent.
     *
     * @return a data as a single value, nullable.
     */
    public DataType fetchFirst() {
        try {
            if (first == null) {
                if (list == null || list.isEmpty()) {
                    if (map != null && !map.isEmpty()) return map.keySet().stream().findFirst().get();
                    return null;
                }
                return list.get(0);
            }
            return first;
        } finally {
            release();
        }
    }

    /**
     * Return a value "as is".
     *
     * @return a list of data, nullable.
     */
    public List<DataType> getList() {
        try {
            return list;
        } finally {
            release();
        }
    }

    /**
     * Expensive version of {@code getList()}, which try to get a list value from a single value or a map, if a list is absent.
     *
     * @return a data as a list value, not nullable.
     */
    public List<DataType> fetchList() {
        try {
            if (list == null) {
                if (first == null) {
                    if (map != null && !map.isEmpty()) return new ArrayList<>(map.keySet());
                    return List.of();
                }
                return List.of(first);
            }
            return list;
        } finally {
            release();
        }
    }

    /**
     * Return a value "as is".
     *
     * @return an aggregation of data, nullable.
     */
    public Map<DataType, Integer> getAggregation() {
        try {
            return map;
        } finally {
            release();
        }
    }

    /**
     * Expensive version of {@code getAggregation()}, which try to get an aggregation value from a single value or a list, if an aggregation is absent.
     *
     * @return a data as an aggregation value, not nullable.
     */
    public Map<DataType, Integer> fetchAggregation() {
        try {
            if (map == null) {
                if (first == null) {
                    if (list != null && !list.isEmpty()) {
                        map = new LinkedHashMap<>();
                        for (DataType e : list) {
                            map.merge(e, 1, Integer::sum);
                        }
                        list.forEach(e -> map.put(e, 1));
                        return map;
                    }
                    return Map.of();
                }
                return Map.of(first, 1);
            }
            return map;
        } finally {
            release();
        }
    }

    private void release() {
        id = null;
        first = null;
        list = null;
        map = null;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "id='" + id + '\'' +
                ", first=" + first +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}

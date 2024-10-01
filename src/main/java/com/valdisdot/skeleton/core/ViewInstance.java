package com.valdisdot.skeleton.core;

import java.util.Map;
import java.util.Optional;

/**
 * The interface represents an abstraction between graphical view elements, their control and data exchange.
 *
 * @param <ContainerViewType> the view type of the container.
 * @param <ComponentViewType> the view type of internal view components.
 * @param <DataType>          type of data exchange between view components and business logic.
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface ViewInstance<ContainerViewType, ComponentViewType, DataType> extends Identifiable {
    /**
     * @param id an id of the required DataUnit.
     * @return an Optional of DataUnit from this view with type {@code <DataType>}
     */
    Optional<DataUnit<DataType>> getDataUnit(String id);

    /**
     * @return a Map of pairs of {@code DataUnit} ID and the {@code DataUnit} itself.
     */
    Map<String, DataUnit<DataType>> getDataUnits();

    /**
     * @param id an id of the required PresentableUnit.
     * @return an Optional of PresentableUnit from this view with type {@code <ComponentViewType>}
     */
    Optional<PresentableUnit<ComponentViewType>> getPresentableUnit(String id);

    /**
     * @return a Map of pairs of {@code PresentableUnit} ID and the {@code PresentableUnit} itself.
     */
    Map<String, PresentableUnit<ComponentViewType>> getPresentableUnits();

    /**
     * @param id an id of the required ControlUnit.
     * @return an Optional of ControlUnit from this view with type {@code <ComponentViewType>}
     * @apiNote Notice that the ControlUnit object must be also accessible via {@code getPresentableUnit(String id)} as long as ControlUnit is an inheritor of the PresentableUnit.
     */
    Optional<ControlUnit<ComponentViewType>> getControlUnit(String id);

    /**
     * @return a Map of pairs of {@code ControlUnit} ID and the {@code ControlUnit} itself.
     * @apiNote Notice that the objects of ControlUnit must be also included in the {@code getPresentableUnits()} as long as ControlUnit is an inheritor of the PresentableUnit.
     */
    Map<String, ControlUnit<ComponentViewType>> getControlUnits();

    /**
     * @return a container's view representation.
     */
    ContainerViewType getView();

    /**
     * @return a properties, which are specified for this view.
     */
    PropertiesMap getProperties();
}
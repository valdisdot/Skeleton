package com.valdisdot.skeleton.core;

import java.util.Collection;
import java.util.Optional;

/**
 * The interface represents a special factory object, which can be used for retrieving view instances and common properties for the application.
 *
 * @param <ContainerViewType> the view type of the container.
 * @param <ComponentViewType> the view type of internal view components.
 * @param <DataType>          type of data exchange between view components and business logic.
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface ViewInstanceProvider<ContainerViewType, ComponentViewType, DataType> extends Identifiable {
    /**
     * @param id an id of the required ViewInstance.
     * @return an Optional of ViewInstance from this provider with types {@code <ContainerViewType, ComponentViewType, DataType>}
     */
    Optional<ViewInstance<ContainerViewType, ComponentViewType, DataType>> getInstance(String id);

    /**
     * @return a properties with common values for the whole application.
     */
    PropertiesMap getProperties();

    /**
     * Allows to peek the instance IDs.
     * @return a collection of IDs
     */
    Collection<String> getInstanceIds();
}

package com.valdisdot.skeleton.core;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ViewInstanceProvider} interface represents a specialized factory object that is responsible
 * for retrieving view instances and providing common properties for the entire application.
 * <p>
 * It handles the management and lookup of {@link ViewInstance} objects, and allows accessing shared
 * properties of the application through a {@link PropertiesMap}.
 * </p>
 *
 * @param <ContainerViewType> the type representing the container view of the view instance.
 * @param <ComponentViewType> the type representing the internal view components of the view instance.
 * @param <DataType>          the type of data exchanged between view components and the business logic.
 *
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface ViewInstanceProvider<ContainerViewType, ComponentViewType, DataType> extends Identifiable {

    /**
     * Retrieves a {@link ViewInstance} by its unique ID.
     *
     * @param id the ID of the required {@link ViewInstance}.
     * @return an {@link Optional} containing the {@link ViewInstance} with types
     *         {@code <ContainerViewType, ComponentViewType, DataType>} if found, or an empty Optional if not.
     */
    Optional<ViewInstance<ContainerViewType, ComponentViewType, DataType>> getInstanceById(String id);

    /**
     * Retrieves a list of {@link ViewInstance} objects that have IDs containing the specified token.
     *
     * @param idToken a partial string token to search for matching instance IDs.
     * @return a list of {@link ViewInstance} objects whose IDs contain the given token.
     */
    List<ViewInstance<ContainerViewType, ComponentViewType, DataType>> getInstancesByIdsLike(String idToken);

    /**
     * Provides a {@link PropertiesMap} containing common values that are shared across the application.
     *
     * @return a {@link PropertiesMap} with application-wide properties.
     */
    PropertiesMap getProperties();

    /**
     * Retrieves the collection of all available instance IDs.
     *
     * @return a collection of instance IDs available in this provider.
     */
    Collection<String> getInstanceIds();
}

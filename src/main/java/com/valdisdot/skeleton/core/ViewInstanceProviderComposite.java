package com.valdisdot.skeleton.core;

import javax.swing.*;
import java.util.Optional;

/**
 * The interface represents a container for storing the ViewInstanceProviders for cases, where the application has more than one provider.
 *
 * @param <ContainerViewType> the view type of the container.
 * @param <ComponentViewType> the view type of internal view components.
 * @param <DataType>          type of data exchange between view components and business logic.
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public interface ViewInstanceProviderComposite<ContainerViewType, ComponentViewType, DataType> {
    /**
     * Adds a view instance provider to the storage.
     *
     * @param viewInstanceProvider a view instance provider
     */
    void add(ViewInstanceProvider<ContainerViewType, ComponentViewType, DataType> viewInstanceProvider);

    /**
     * Gets instance from a provider.
     *
     * @param providerId a provider id
     * @param instanceId an instance id
     * @return an optional of the instance
     */
    Optional<ViewInstance<JPanel, JComponent, String>> getInstance(String providerId, String instanceId);

    /**
     * Gets specified for a provider properties.
     *
     * @param providerId a provider id
     * @return properties from the provider
     */
    PropertiesMap getProperties(String providerId);
}

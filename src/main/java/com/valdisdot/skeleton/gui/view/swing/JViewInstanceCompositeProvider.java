package com.valdisdot.skeleton.gui.view.swing;

import com.valdisdot.skeleton.core.ViewInstance;
import com.valdisdot.skeleton.core.ViewInstanceProvider;
import com.valdisdot.skeleton.core.ViewInstanceProviderComposite;
import com.valdisdot.skeleton.util.PropertiesMap;

import javax.swing.*;
import java.util.*;

/**
 * The class represents the default implementation of the ViewInstanceProviderComposite for cases, where the application has more than one provider.
 * Also, it implements ViewInstanceProvider and store merged instances and properties from the internal ViewInstanceProviders.
 *
 * @author Vladyslav Tymchenko
 * @since 1.0
 */
public class JViewInstanceCompositeProvider implements ViewInstanceProviderComposite<JPanel, JComponent, String>, ViewInstanceProvider<JPanel, JComponent, String> {
    private final String id;
    private final boolean allowCollision;
    private final Map<String, ViewInstanceProvider<JPanel, JComponent, String>> unfoldedComposite = new HashMap<>();
    private final PropertiesMap mergedPropertiesMap = new PropertiesMap();

    private final Map<String, ViewInstanceProvider<JPanel, JComponent, String>> composite = new HashMap<>();

    /**
     * Instantiates an object of the composite provider, with the ID and the flag for checking the appearing of the IDs collision.
     *
     * @param id             an id, nullable
     * @param allowCollision flag to allow collision
     */
    public JViewInstanceCompositeProvider(String id, boolean allowCollision) {
        this.id = id;
        this.allowCollision = allowCollision;
    }

    /**
     * Instantiates an object of the composite provider, without the ID and with throwing IllegalArgumentException if IDs collision appears.
     *
     * @see IllegalArgumentException
     */
    public JViewInstanceCompositeProvider() {
        this(null, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(ViewInstanceProvider<JPanel, JComponent, String> provider) throws IllegalArgumentException {
        if (allowCollision) {
            for (String instanceId : provider.getInstanceIds()) unfoldedComposite.put(instanceId, provider);
            mergedPropertiesMap.putAll(provider.getProperties());
        } else {
            for (String instanceId : provider.getInstanceIds()) {
                if (unfoldedComposite.containsKey(instanceId))
                    throw new IllegalArgumentException(String.format("Collision with provider ID: %s. ID is already defined in the provider with ID: %s, not just in provider with ID: %s", instanceId, unfoldedComposite.get(instanceId).getId(), provider.getId()));
                unfoldedComposite.put(instanceId, provider);
            }
            for (Map.Entry<String, Object> objectEntry : provider.getProperties().entrySet()) {
                if (mergedPropertiesMap.containsKey(objectEntry.getKey()))
                    throw new IllegalArgumentException(String.format("Collision with property ID: %s in the provider with ID: %s", objectEntry.getKey(), provider.getId()));
                mergedPropertiesMap.put(objectEntry.getKey(), objectEntry.getValue());
            }
        }
        composite.put(provider.getId(), provider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ViewInstance<JPanel, JComponent, String>> getInstance(String providerId, String instanceId) {
        return composite.containsKey(providerId) ? composite.get(providerId).getInstance(instanceId) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertiesMap getProperties(String providerId) {
        return composite.containsKey(providerId) ? composite.get(providerId).getProperties() : new PropertiesMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getInstanceIds() {
        HashSet<String> idsSet = new HashSet<>();
        composite.values().forEach(provider -> idsSet.addAll(provider.getInstanceIds()));
        return idsSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ViewInstance<JPanel, JComponent, String>> getInstance(String id) {
        return unfoldedComposite.containsKey(id) ? unfoldedComposite.get(id).getInstance(id) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertiesMap getProperties() {
        return mergedPropertiesMap;
    }
}

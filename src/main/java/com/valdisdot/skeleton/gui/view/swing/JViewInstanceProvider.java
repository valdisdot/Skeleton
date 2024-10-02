package com.valdisdot.skeleton.gui.view.swing;

import com.valdisdot.skeleton.core.ViewInstance;
import com.valdisdot.skeleton.core.ViewInstanceProvider;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.gui.parser.provider.MoldProvider;
import com.valdisdot.skeleton.core.PropertiesMap;

import javax.swing.*;
import java.util.*;

/**
 * The {@code JViewInstanceProvider} class represents an implementation of the {@link ViewInstanceProvider} interface
 * for Swing-based applications, specifically handling {@link PanelMold} instances.
 * <p>
 * This class provides a factory object that retrieves view instances and common application properties.
 * It supports both precompiled (singleton) and dynamically created (prototype) views.
 * </p>
 *
 * @since 1.0
 * @see ViewInstanceProvider
 * @see PanelMold
 * @see MoldProvider
 * @see PropertiesMap
 * @see JViewInstance
 * @see JPanel
 * @see JComponent
 * @see String
 *
 * @author Vladyslav Tymchenko
 */
public class JViewInstanceProvider implements ViewInstanceProvider<JPanel, JComponent, String> {
    private String id;
    private final Map<String, JViewInstance> compiledViews;
    private final Map<String, PanelMold> moldedViews;
    private final PropertiesMap propertiesMap;

    /**
     * Instantiates a new {@code JViewInstanceProvider} using a {@link MoldProvider}.
     * <p>
     * The provider is used to gather precompiled singleton views and prototype molds.
     * The properties from the provider are copied into this instance's {@link PropertiesMap}.
     * </p>
     *
     * @param provider the {@link MoldProvider} containing {@link PanelMold} instances and properties.
     */
    public JViewInstanceProvider(MoldProvider provider) {
        this.id = provider.getId();
        compiledViews = new HashMap<>();
        moldedViews = new HashMap<>();
        propertiesMap = new PropertiesMap();
        provider.getPanelMolds().forEach(panelMold -> {
            if (panelMold.getScope() == PanelMold.Scope.PROTOTYPE) {
                moldedViews.put(panelMold.getId(), panelMold);
            } else {
                compiledViews.put(panelMold.getId(), new JViewInstance(panelMold));
            }
        });
        propertiesMap.putAll(provider.getProperties());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a collection of all available instance IDs, which include both compiled and molded views.
     * </p>
     */
    @Override
    public Collection<String> getInstanceIds() {
        Set<String> keySet = new HashSet<>();
        keySet.addAll(compiledViews.keySet());
        keySet.addAll(moldedViews.keySet());
        return keySet;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the ID of this {@code JViewInstanceProvider}.
     * </p>
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the {@link PanelMold} associated with the given ID is defined as {@code PanelMold.Scope.SINGLETON},
     * a precompiled {@link JViewInstance} is returned. Otherwise, a new instance is constructed each time
     * the method is called if the mold is of {@code PROTOTYPE} scope.
     * </p>
     *
     * @apiNote If the {@code PanelMold} has a scope of {@code SINGLETON}, the method will return a precompiled {@link JViewInstance}.
     *          Otherwise, it constructs a new instance for every call.
     *
     * @param id the ID of the desired view instance.
     * @return an {@link Optional} containing the view instance if found, or empty if not.
     */
    @Override
    public Optional<ViewInstance<JPanel, JComponent, String>> getInstanceById(String id) {
        return compiledViews.containsKey(id) ? Optional.ofNullable(compiledViews.get(id)) :
                moldedViews.containsKey(id) ? Optional.of(new JViewInstance(moldedViews.get(id))) :
                        Optional.empty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a list of view instances whose IDs contain the provided token.
     * This method searches both compiled and molded views.
     * </p>
     *
     * @param idToken a string token to search for in instance IDs.
     * @return a list of view instances whose IDs match the token.
     */
    @Override
    public List<ViewInstance<JPanel, JComponent, String>> getInstancesByIdsLike(String idToken) {
        List<ViewInstance<JPanel, JComponent, String>> res = new LinkedList<>();
        for(String key : compiledViews.keySet()) if(key.contains(idToken)) res.add(compiledViews.get(key));
        for(String key : moldedViews.keySet()) if(key.contains(idToken)) res.add(new JViewInstance(moldedViews.get(key)));
        return res;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the {@link PropertiesMap} containing shared properties for the application.
     * </p>
     */
    @Override
    public PropertiesMap getProperties() {
        return propertiesMap;
    }
}

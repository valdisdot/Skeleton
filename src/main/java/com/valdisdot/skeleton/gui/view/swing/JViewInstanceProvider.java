package com.valdisdot.skeleton.gui.view.swing;

import com.valdisdot.skeleton.core.ViewInstance;
import com.valdisdot.skeleton.core.ViewInstanceProvider;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.gui.parser.provider.MoldProvider;
import com.valdisdot.skeleton.util.PropertiesMap;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class represents the implementation for Molds of a special factory object, which can be used for retrieving view instances and common properties for the application.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class JViewInstanceProvider implements ViewInstanceProvider<JPanel, JComponent, String> {
    private final Map<String, JViewInstance> compiledViews;
    private final Map<String, PanelMold> moldedViews;
    private final PropertiesMap propertiesMap;

    /**
     * Instantiates a new view instance provider.
     *
     * @param provider the provider
     */
    public JViewInstanceProvider(MoldProvider provider) {
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
     *     @apiNote Note that if the scope of the defined mold is {@code PanelMold.Scope.SINGLETON}, the method will return a precompiled it the construction JView if present. Otherwise, the method will construct a new JView each time the method called.
     * </p>
     */
    @Override
    public Optional<ViewInstance<JPanel, JComponent, String>> getInstance(String id) {
        return compiledViews.containsKey(id) ? Optional.ofNullable(compiledViews.get(id)) :
                moldedViews.containsKey(id) ? Optional.of(new JViewInstance(moldedViews.get(id))) :
                        Optional.empty();
    }

    /**{@inheritDoc}*/
    @Override
    public PropertiesMap getProperties() {
        return propertiesMap;
    }
}

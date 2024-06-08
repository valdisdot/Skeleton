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

public class JViewInstanceProvider implements ViewInstanceProvider<JPanel, JComponent, String> {
    private final Map<String, JView> compiledViews;
    private final Map<String, PanelMold> moldedViews;
    private final PropertiesMap propertiesMap;

    public JViewInstanceProvider(MoldProvider provider) {
        compiledViews = new HashMap<>();
        moldedViews = new HashMap<>();
        propertiesMap = new PropertiesMap();
        provider.getPanelMolds().forEach(panelMold -> {
            if (panelMold.getScope() == PanelMold.Scope.PROTOTYPE) {
                moldedViews.put(panelMold.getId(), panelMold);
            } else {
                compiledViews.put(panelMold.getId(), new JView(panelMold));
            }
        });
        propertiesMap.putAll(provider.getProperties());
    }

    @Override
    public Optional<ViewInstance<JPanel, JComponent, String>> getInstance(String id) {
        return compiledViews.containsKey(id) ? Optional.ofNullable(compiledViews.get(id)) :
                moldedViews.containsKey(id) ? Optional.of(new JView(moldedViews.get(id))) :
                        Optional.empty();
    }

    @Override
    public PropertiesMap getProperties() {
        return propertiesMap;
    }
}

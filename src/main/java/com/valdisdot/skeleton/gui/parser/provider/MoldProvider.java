package com.valdisdot.skeleton.gui.parser.provider;

import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.util.PropertiesMap;

import java.util.Collection;

public interface MoldProvider {
    Collection<PanelMold> getPanelMolds();

    PropertiesMap getProperties();
}

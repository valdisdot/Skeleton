package com.valdisdot.skeleton.gui.parser.provider;

import com.valdisdot.skeleton.core.Identifiable;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import java.util.Collection;
import java.util.Map;

/**
 * The interface provide an additional abstraction layer for parsing and deserializing PanelMolds from the outsize the application, like JSON- or XML-files.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public interface MoldProvider extends Identifiable {
    /**
     * @return a collection of panel molds
     * @see PanelMold
     */
    Collection<PanelMold> getPanelMolds();

    /**
     * @return the common properties for the whole application.
     */
    Map<String, String> getProperties();
}

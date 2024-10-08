package com.valdisdot.skeleton.gui.parser.provider;

import com.valdisdot.skeleton.core.Identifiable;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.gui.parser.mold.Style;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    List<PanelMold> getPanelMolds();

    /**
     * @return an Optional of the panel mold by its ID
     * @see PanelMold
     */
    Optional<PanelMold> getPanelMoldById(String panelMoldId);

    /**
     * @return the common properties for the whole application.
     */
    Map<String, String> getProperties();

    /**
     * @return the application's styles.
     */
    Map<String, Style> getStyles();
}

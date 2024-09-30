package com.valdisdot.skeleton.gui.parser.provider.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdisdot.skeleton.gui.parser.mold.ElementMold;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.gui.parser.mold.Style;
import com.valdisdot.skeleton.gui.parser.provider.MoldProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

/**
 * The implementation of MoldProvider for JSON-files.
 * @apiNote Uses Jackson API under the hood.
 * @see MoldProvider
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class JsonMoldProvider implements MoldProvider {
    private String id;
    private final Map<String, String> properties;
    private final Collection<PanelMold> panelMolds;
    private final Map<String, Style> styles;

    /**
     * Instantiates a mold provider from the json file.
     *
     * @param json a json file
     * @throws IOException an input/output exception, if something went wrong with the file
     */
    public JsonMoldProvider(File json) throws IOException {
        this(new ObjectMapper().readValue(json, JsonPlot.class));
    }

    /**
     * Instantiates a mold provider from the json string.
     *
     * @param json a json string
     * @throws JsonProcessingException the json processing exception, if something went wrong with the json structure in the string.
     */
    public JsonMoldProvider(String json) throws JsonProcessingException {
        this(new ObjectMapper().readValue(json, JsonPlot.class));
    }

    /**
     * Instantiates a mold provider from the stream.
     *
     * @param json a json input stream
     * @throws IOException an input/output exception
     */
    public JsonMoldProvider(InputStream json) throws IOException {
        this(new ObjectMapper().readValue(json, JsonPlot.class));
    }

    /**
     * Instantiates a mold provider from a URI of json location.
     *
     * @param json a json URI
     * @throws IOException an input/output exception
     */
    public JsonMoldProvider(URI json) throws IOException {
        this(new ObjectMapper().readValue(json.toURL(), JsonPlot.class));
    }

    /**
     * Instantiates a mold provider from a POJO. Contain the main parsing logic.
     *
     * @param json the json POJO
     */
    protected JsonMoldProvider(JsonPlot json) {
        Objects.requireNonNull(json, "JsonPlot is null");
        this.id = json.getId();
        properties = new LinkedHashMap<>();
        properties.putAll(json.getProperties());

        styles = new HashMap<>();
        styles.put("default_background", new Style("default_background", Style.Keyword.BACKGROUND_COLOR, Map.of(Style.Keyword.COLOR, "#ffffff")));
        styles.put("default_foreground", new Style("default_foreground", Style.Keyword.FOREGROUND_COLOR, Map.of(Style.Keyword.COLOR, "#000000")));
        styles.put("default_font", new Style("default_font", Style.Keyword.FONT, Map.of(Style.Keyword.FONT_NAME, "Arial", Style.Keyword.FONT_STYLE_KEY, "plain", Style.Keyword.FONT_SIZE, "12")));
        styles.putAll(parseStyles(json.getStyles()));

        panelMolds = new ArrayList<>(json.getPanels().size());
        panelMolds.addAll(parsePanelMolds(json.getPanels(), styles));
    }

    private Map<String, Style> parseStyles(List<JsonPlot.StylePlot> stylePlots) {
        Map<String, Style> styles = new HashMap<>();
        Style.Keyword type;
        Map<String, String> values;
        Map<Style.Keyword, String> parsedValues;
        for (JsonPlot.StylePlot stylePlot : stylePlots) {
            Objects.requireNonNull(stylePlot.getId(), "ID for style element is null: " + stylePlot);
            Objects.requireNonNull(stylePlot.getType(), "Type for style element is null: " + stylePlot);
            if (stylePlot.getValues().isEmpty())
                throw new NoSuchElementException("Value for style element is null: " + stylePlot);
            type = Style.Keyword.fromValue(stylePlot.getType());
            values = stylePlot.getValues();
            switch (type) {
                case SIZE: {
                    if (!(values.containsKey(Style.Keyword.WIDTH.getValue()) && values.containsKey(Style.Keyword.HEIGHT.getValue())))
                        throw new NoSuchElementException("Essential arguments for a size element is missing: " + stylePlot);
                    break;
                }
                case FONT: {
                    if (!(values.containsKey(Style.Keyword.FONT_NAME.getValue()) && values.containsKey(Style.Keyword.FONT_STYLE_KEY.getValue()) && values.containsKey(Style.Keyword.FONT_SIZE.getValue())))
                        throw new NoSuchElementException("Essential arguments for a font element is missing: " + stylePlot);
                    break;
                }
                case BACKGROUND_COLOR:
                case FOREGROUND_COLOR: {
                    if (!(values.containsKey(Style.Keyword.COLOR.getValue())))
                        throw new NoSuchElementException("Essential arguments for a color element is missing: " + stylePlot);
                    break;
                }
                default: {
                    break;
                }
            }
            parsedValues = new HashMap<>();
            for (Map.Entry<String, String> entry : values.entrySet())
                parsedValues.put(Style.Keyword.fromValue(entry.getKey()), Objects.requireNonNull(entry.getValue(), "Value for property '" + entry.getKey() + "' is null. Style element: " + stylePlot));
            styles.put(stylePlot.getId(), new Style(stylePlot.getId(), type, parsedValues));
        }
        return styles;
    }

    private Collection<PanelMold> parsePanelMolds(List<JsonPlot.PanelPlot> panels, Map<String, Style> styles) {
        List<PanelMold> panelMolds = new LinkedList<>();
        panels.forEach(panelPlot -> {
            //is not null
            PanelMold mold = new PanelMold(
                    Objects.requireNonNull(panelPlot.getId(), "ID for panel element is null: " + panelPlot),
                    "prototype".equalsIgnoreCase(panelPlot.getScope()) ? PanelMold.Scope.PROTOTYPE : PanelMold.Scope.SINGLETON);
            //may be null
            mold.setTitle(panelPlot.getTitle());
            //may be empty
            mold.addProperties(panelPlot.getProperties());
            //bind styles
            panelPlot.getStyles().forEach(styleId -> {
                if (styles.containsKey(styleId)) mold.addStyle(styles.get(styleId));
            });
            mold.setDefaultStyleTypeIfAbsent(styles.get("default_background"));
            mold.setDefaultStyleTypeIfAbsent(styles.get("default_foreground"));
            mold.setDefaultStyleTypeIfAbsent(styles.get("default_font"));
            //parse element molds
            mold.addElements(parseElementMolds(panelPlot.getElements(), styles));
            //bind panel styles if absent in element
            mold.getElements().forEach(eMold -> {
                eMold.setDefaultStyleTypeIfAbsent(mold.getStyle(Style.Keyword.BACKGROUND_COLOR));
                eMold.setDefaultStyleTypeIfAbsent(mold.getStyle(Style.Keyword.FOREGROUND_COLOR));
                eMold.setDefaultStyleTypeIfAbsent(mold.getStyle(Style.Keyword.FONT));
            });
            panelMolds.add(mold);
        });
        return panelMolds;
    }

    private Collection<ElementMold> parseElementMolds(List<JsonPlot.ElementPlot> elements, Map<String, Style> styles) {
        List<ElementMold> elementMolds = new LinkedList<>();
        elements.forEach(elementPlot -> {
            ElementMold elementMold = new ElementMold(
                    Objects.requireNonNull(elementPlot.getId(), "ID for element is null: " + elementPlot),
                    elementPlot.getType(),
                    elementPlot.getValues()
            );
            elementMold.addProperties(elementPlot.getProperties());
            elementMold.doFollow(elementPlot.isFollow());
            elementMold.setTitle(elementPlot.getTitle());
            elementMold.setControl(elementPlot.getType().contains("control"));
            //bind styles
            elementPlot.getStyles().forEach(styleId -> {
                if (styles.containsKey(styleId)) elementMold.addStyle(styles.get(styleId));
            });
            elementMolds.add(elementMold);
        });
        return elementMolds;
    }

    /**{@inheritDoc}*/
    @Override
    public String getId() {
        return id;
    }

    /**{@inheritDoc}*/
    @Override
    public Collection<PanelMold> getPanelMolds() {
        return panelMolds;
    }

    /**{@inheritDoc}*/
    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    /**{@inheritDoc}*/
    @Override
    public Map<String, Style> getStyles() {
        return styles;
    }
}

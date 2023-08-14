package com.valdisdot.util.ui.gui.parser.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.parser.GUIParser;
import com.valdisdot.util.ui.gui.parser.mold.ElementMold;
import com.valdisdot.util.ui.gui.parser.mold.FrameMold;
import com.valdisdot.util.ui.gui.parser.mold.PanelMold;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
see example of a json template:
https://github.com/valdisdot/Utilities sketch/gui.json
 */
public class JsonGUIParser implements GUIParser {
    private final List<FrameMold> frameMolds;

    public JsonGUIParser(File jsonFile) throws IOException {
        this(new ObjectMapper().readValue(jsonFile, JsonApplicationPlot.class));
    }

    public JsonGUIParser(String json) throws JsonProcessingException {
        this(new ObjectMapper().readValue(json, JsonApplicationPlot.class));
    }

    public JsonGUIParser(InputStream jsonInputStream) throws IOException {
        this(new ObjectMapper().readValue(jsonInputStream, JsonApplicationPlot.class));
    }

    public JsonGUIParser(URI uriToJson) throws IOException {
        this(new ObjectMapper().readValue(uriToJson.toURL(), JsonApplicationPlot.class));
    }

    protected JsonGUIParser(JsonApplicationPlot rootPlot) {
        JsonPropertyHolder properties = new JsonPropertyHolder();
        //parse application common properties
        parseColors(rootPlot.getProperties().getColors(), Color.LIGHT_GRAY, Color.BLACK, properties);
        parseFonts(rootPlot.getProperties().getFonts(), new Font("Arial", Font.PLAIN, 12), properties);
        parseSizes(rootPlot.getProperties().getSizes(), properties);
        //parse frames
        frameMolds = new ArrayList<>(rootPlot.getFrames().size());
        rootPlot.getFrames().forEach(framePlot -> {
            Objects.requireNonNull(framePlot.getName(), "Frame name is null. " + framePlot);
            Objects.requireNonNull(framePlot.getTitle(), "Frame title is null. " + framePlot);
            FrameMold frameMold = new FrameMold(framePlot.getName(), framePlot.getTitle());
            frameMold.setRootBackgroundColor(ValuesParser.fromHEXToDecimalInt(framePlot.getRootBackground(), Color.LIGHT_GRAY.getRGB()));
            parsePanelMolds(framePlot.getPanels(), properties, frameMold);
            frameMolds.add(frameMold);
        });
    }

    //properties parsing
    //throws NullPointerException and IllegalArgumentException if you are not following the syntax of gui.json
    protected void parseColors(Iterable<JsonApplicationPlot.ColorsMap> map, Color defaultBackground, Color defaultForeground, JsonPropertyHolder propertyHolder) {
        map.forEach(colorsMap -> {
            Objects.requireNonNull(colorsMap.getName(), "Name for color element is null. " + colorsMap);
            Objects.requireNonNull(colorsMap.getType(), "Type for color element is null. " + colorsMap);
            Objects.requireNonNull(colorsMap.getValue(), "Value for color element is null. " + colorsMap);
            colorsMap.setType(colorsMap.getType().toLowerCase()); //bACkgROunD -> background
            if (!(colorsMap.getType().equals(SyntaxTag.BACKGROUND_VALUE.getValue()) || colorsMap.getType().equals(SyntaxTag.FOREGROUND_VALUE.getValue())))
                throw new IllegalArgumentException("Unknown color type. " + colorsMap);
            if (colorsMap.getType().equals(SyntaxTag.BACKGROUND_VALUE.getValue())) {
                //colorMap.name = 'myColor' -> background_myColor
                propertyHolder.addColor(colorsMap.getType() + "_" + colorsMap.getName(), ValuesParser.fromHEXToDecimalInt(colorsMap.getValue(), -1), defaultBackground);
            } else {
                propertyHolder.addColor(colorsMap.getType() + "_" + colorsMap.getName(), ValuesParser.fromHEXToDecimalInt(colorsMap.getValue(), -1), defaultForeground);
            }
        });
    }

    protected void parseFonts(Iterable<JsonApplicationPlot.FontsMap> map, Font defaultFont, JsonPropertyHolder propertyHolder) {
        map.forEach(fontsMap -> {
            Objects.requireNonNull(fontsMap.getName(), "Name for font element is null. " + fontsMap);
            Objects.requireNonNull(fontsMap.getFontName(), "Font family name is null. " + fontsMap);
            propertyHolder.addFont(fontsMap.getName(), fontsMap.getFontName(), fontsMap.getFontStyle(), fontsMap.getFontSize(), defaultFont);
        });
    }

    protected void parseSizes(Iterable<JsonApplicationPlot.SizesMap> map, JsonPropertyHolder propertyHolder) {
        map.forEach(sizesMap -> {
            Objects.requireNonNull(sizesMap.getName(), "Name for font element is null. " + sizesMap);
            propertyHolder.addSize(sizesMap.getName(), sizesMap.getWidth(), sizesMap.getHeight());
        });
    }

    protected void parsePanelMolds(Iterable<JsonApplicationPlot.PanelPlot> panelPlots, JsonPropertyHolder propertyHolder, FrameMold frameMold) {
        panelPlots.forEach(panelPlot -> {
            //init panel mold instance
            PanelMold panelMold = new PanelMold(panelPlot.isFromTheTopOfFrame());
            //parse default panel plot properties
            Color panelBackground = null;
            Color defaultForeground = null;
            Font defaultFont = null;
            for (String property : panelPlot.getProperties()) {
                property = propertyHolder.getRelevantKey(property);
                if (propertyHolder.containsKey(property)) {
                    if (Color.class.equals(propertyHolder.getTypeForKey(property).orElseThrow())) {
                        if (property.contains(SyntaxTag.BACKGROUND_VALUE.getValue()))
                            panelBackground = propertyHolder.getProperty(property, Color.class).orElseThrow();
                        else if (property.contains(SyntaxTag.FOREGROUND_VALUE.getValue())) {
                            defaultForeground = propertyHolder.getProperty(property, Color.class).orElseThrow();
                        }
                    } else if (Font.class.equals(propertyHolder.getTypeForKey(property).orElseThrow())) {
                        defaultFont = propertyHolder.getProperty(property, Font.class).orElseThrow();
                    }
                }
            }

            if (Objects.nonNull(panelBackground)) panelMold.setBackgroundColor(panelBackground.getRGB());

            for (JsonApplicationPlot.ElementPlot elementPlot : panelPlot.getElements()) {
                Objects.requireNonNull(elementPlot.getType(), "Type for element is null. " + elementPlot);
                //init element mold instance
                ElementMold elementMold = new ElementMold();
                //parse element plot properties
                Color background = panelBackground;
                Color foreground = defaultForeground;
                Font font = defaultFont;
                Dimension preferredSize = null;

                for (String property : elementPlot.getProperties()) {
                    property = propertyHolder.getRelevantKey(property);
                    if (propertyHolder.containsKey(property)) {
                        if (Color.class.equals(propertyHolder.getTypeForKey(property).orElseThrow())) {
                            if (property.contains(SyntaxTag.BACKGROUND_VALUE.getValue()))
                                background = propertyHolder.getProperty(property, Color.class).orElse(panelBackground);
                            else if (property.contains(SyntaxTag.FOREGROUND_VALUE.getValue())) {
                                foreground = propertyHolder.getProperty(property, Color.class).orElse(defaultForeground);
                            }
                        } else if (Font.class.equals(propertyHolder.getTypeForKey(property).get())) {
                            font = propertyHolder.getProperty(property, Font.class).orElse(defaultFont);
                        } else if (Dimension.class.equals(propertyHolder.getTypeForKey(property).get())) {
                            Optional<Dimension> dimension = propertyHolder.getProperty(property, Dimension.class);
                            if (dimension.isPresent()) preferredSize = dimension.get();
                        }
                    }
                }

                if (Objects.nonNull(background)) elementMold.setBackgroundColor(background.getRGB());
                if (Objects.nonNull(foreground)) elementMold.setForegroundColor(foreground.getRGB());
                if (Objects.nonNull(font)) {
                    elementMold.setFontName(font.getFontName());
                    elementMold.setFontSize(font.getSize());
                    elementMold.setFontStyle(String.valueOf(font.getStyle()));
                }
                if (Objects.nonNull(preferredSize)) {
                    elementMold.setWidth(preferredSize.width);
                    elementMold.setHeight(preferredSize.height);
                }
                elementMold.setName(elementPlot.getName());
                elementMold.setType(elementPlot.getType());
                elementMold.setTitle(elementPlot.getTitle());
                elementMold.setValues(elementPlot.getValues());

                if (SyntaxTag.ROW_VALUE.getValue().equals(elementPlot.getLaying())) {
                    panelMold.addToRow(elementMold);
                } else if (SyntaxTag.NEW_ROW_VALUE.getValue().equals(elementPlot.getLaying())) {
                    panelMold.addToNewRow(elementMold);
                } else panelMold.add(elementMold);
            }
            frameMold.addPanelMold(panelMold);
        });
    }

    @Override
    public List<FrameMold> get() {
        return frameMolds;
    }

    public enum SyntaxTag {
        BACKGROUND_VALUE("background"),
        FOREGROUND_VALUE("foreground"),
        ROW_VALUE("row"),
        NEW_ROW_VALUE("newRow");

        private final String value;

        SyntaxTag(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

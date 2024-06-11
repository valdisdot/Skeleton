package com.valdisdot.skeleton.gui.parser.provider.json;

import java.io.*;
import java.util.*;

/**
 * The class represents a POJO object for json parsing.
 * @apiNote Mainly for internal use.
 * */
public class JsonPlot {
    private final Map<String, Object> properties = new LinkedHashMap<>();
    private final List<StylePlot> styles = new LinkedList<>();
    private final List<PanelPlot> panels = new LinkedList<>();

    public static void compileJsonStarterFile(File destination) throws IOException {
        if (destination.length() > 0)
            throw new IOException(String.format("File '%s' already exists!", destination.getName()));
        FileWriter writer = new FileWriter(destination);
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(Objects.requireNonNull(JsonPlot.class.getClassLoader().getResourceAsStream("doc"))));
        String tmp;
        String[] tmpArray;
        while (inputStream.ready()) {
            tmp = inputStream.readLine();
            if (tmp.isBlank()) continue;
            if (tmp.contains("##")) {
                tmpArray = tmp.split("##");
                if (tmpArray[0].isBlank()) continue;
                writer.write(tmpArray[0]);
            } else writer.write(tmp);
            writer.write("\n");
        }
        writer.flush();
        inputStream.close();
        writer.close();
    }

    public static void writeDocReference(File destination) throws IOException {
        try (
                FileOutputStream out = new FileOutputStream(destination);
                InputStream in = JsonPlot.class.getClassLoader().getResourceAsStream("doc")
        ) {
            in.transferTo(out);
        }
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    void setProperties(Map<String, Object> properties) {
        this.properties.putAll(properties);
    }

    public List<StylePlot> getStyles() {
        return styles;
    }

    void setStyles(List<StylePlot> styles) {
        this.styles.addAll(styles);
    }

    public List<PanelPlot> getPanels() {
        return panels;
    }

    void setPanels(List<PanelPlot> panels) {
        this.panels.addAll(panels);
    }

    @Override
    public String toString() {
        return "JsonPlot{" +
                "properties=" + properties +
                ", styles=" + styles +
                ", panels=" + panels +
                '}';
    }

    public static class StylePlot {
        private final Map<String, String> values = new HashMap<>();
        private String id;
        private String type;

        {
            values.put("fontStyle", "plain");
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, String> getValues() {
            return values;
        }

        public void setValues(Map<String, String> values) {
            this.values.putAll(values);
        }

        @Override
        public String toString() {
            return "StylePlot{" +
                    "values=" + values +
                    ", id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    private static class Plot {
        protected final List<String> styles = new LinkedList<>();
        protected final Map<String, String> properties = new HashMap<>();
        protected String id;
        protected String title;

        public String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        void setTitle(String title) {
            this.title = title;
        }

        public List<String> getStyles() {
            return styles;
        }

        void setStyles(List<String> styles) {
            this.styles.addAll(styles);
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        void setProperties(Map<String, String> properties) {
            this.properties.putAll(properties);
        }
    }

    public static class ElementPlot extends Plot {
        private String type;
        private boolean follow = false;
        private final Map<String, String> values = new LinkedHashMap<>();

        {
            //defaults
            properties.put("defaultValue", "");
            properties.put("vertical", "false");
            properties.put("listStyle", "linear");
            properties.put("selected", "true");
            properties.put("deselected", "false");
            properties.put("preselected", "false");
        }

        public String getType() {
            return type;
        }

        void setType(String type) {
            this.type = type;
        }

        public boolean isFollow() {
            return follow;
        }

        void setFollow(boolean follow) {
            this.follow = follow;
        }

        public Map<String, String> getValues() {
            return values;
        }

        public void setValues(Map<String, String> values) {
            this.values.putAll(values);
        }

        @Override
        public String toString() {
            return "ElementPlot{" +
                    "type='" + type + '\'' +
                    ", follow=" + follow +
                    ", values=" + values +
                    ", styles=" + styles +
                    ", properties=" + properties +
                    ", id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public static class PanelPlot extends Plot {
        private final List<ElementPlot> elements = new LinkedList<>();
        private String scope = "singleton";

        public List<ElementPlot> getElements() {
            return elements;
        }

        void setElements(List<ElementPlot> elements) {
            this.elements.addAll(elements);
        }

        public String getScope() {
            return scope;
        }

        void setScope(String scope) {
            this.scope = scope;
        }

        @Override
        public String toString() {
            return "PanelPlot{" +
                    "elements=" + elements +
                    ", scope='" + scope + '\'' +
                    ", styles=" + styles +
                    ", properties=" + properties +
                    ", id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}

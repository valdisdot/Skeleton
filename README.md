## Skeleton Library

The library contains classes and methods which makes the development of a simple GUI application much easy, in a functional programming way.

It provides the separation between the business logic (`DataUnit`), control the application via GUI (by `ControlUnit`) and the application view (by `PresentableUnit`) by the `ViewInstance` interface. Clone the repository and see the ***javadoc*** to learn more.

Current implementation contains logic for parsing `javax.swing` classes as a GUI from a *json-source* like file, `InputStream` or a string.

For now, the Skeleton perfectly fits to make a simple graphical application with basic but essential elements like buttons, labels, lists, checkboxes etc. (see `gui.view.swing.unit.presentable`). If the goal is to make a desktop application with an animation or complex GUI, JavaFX is a better option to achieve that.

### Library structure

- `core`

  - `Identifiable` base *interface*
  - `ActionRegistration` subscriber *interface*
  - `ControlUnit` *interface*
  - `DataUnit` *interface*
  - `PresentableUnit` *interface*
  - `Pair` container *class*
  - `ViewInstance` *interface*
  - `ViewInstanceProvider` *interface*
  - `PropertiesMap` container *class*
- `gui`
    - `parser`
        - `mold`
            - `Mold` *abstract class*
            - `ElementMold` *class*
            - `PanelMold` *class*
            - `Style` *class*
        - `provider`
            - `MoldProvider` *interface*
            - `json`
                - `JsonPlot` *class*
                - `JsonMoldProvider` *class*
    - `view`
        - `swing`
            - `JElement` *abstract class*
            - `JViewInstance` *class*
            - `JViewInstanceProvider` *class*
            - `unit`
                - `JPresentableUnit` *abstract class*
                - `JSinglePresentableUnit` *abstract class*
                - `JMultiPresentableUnit` *abstract class*
                - `control`
                    - `ControlButton` *class*
                - `presentable`
                    - `Label` *class*
                    - `ContentButton` *class*
                    - `CheckBox` *class*
                    - `ComboBox` *class*
                    - `MultiComboBox` *class*
                    - `RadioBox` *class*
                    - `TextArea` *class*
                    - `TextField` *class*
                    - `Slider` *class*
                    - `Spinner` *class*
            - `custom`
              - `EditableTable` *class*
              - `FrameBuilder` *class*
- `util`
    - `Functions` *util class*
    - `HelloWorld` *init class*

### Quick start

The library contains a class `util.HelloWorld` with the main method. You can initialize the library to begin the developing or peek through the parsing example.

- build a jar file with `mvn clean install`
- go to the `target`
- run `java -cp skeleton.jar com.valdisdot.skeleton.util.HelloWorld` with one or a couple arguments:
    -  `--doc`          to create a 'doc.txt' file with API references.
    -  `--init`         to create a 'skeleton.json' file with the whole available fields and properties.
    -  `--example`      to show a plain panel with a view example.

### The content of `doc.txt` with description of `skeleton.json` structure

``````
## JsonPresentableUnit API
{
    "id": "skeleton_example_file_id", ## optional field, helps to identify the source
    ## optional object, a map with custom properties, which can be retrieved as a part of PropertiesMap
    "properties": {
        "example_application_name": "Skeleton example!",
        "your_custom_value_2": 1,
        "your_custom_value_3": true
    },
    ## optional array with style properties for elements and panel, can be bound in panels/elements section of this json or in your code later
    ## also can be retrieved as a part of PropertiesMap, but if any styles property is not used, it will be not included in a PropertiesMap
        ## background/foreground -> Color.class
        ## size -> Dimension.class
        ## font -> Font.class
    "styles": [
        {
            ## background color
            "id": "your_background_id", ## mandatory field
            "type": "background", ## mandatory field
            "values": { ## mandatory field
                "color": "#ffffff"
            }
        },
        {
            ## foreground color
            "id": "your_foreground_id", ## mandatory field
            "type": "foreground", ## mandatory field
            "values": { ## mandatory field
                "color": "#000000"
            }
        },
        {
            ## size
            "id": "your_size_id", ## mandatory field
            "type": "size", ## mandatory field
            "values": { ## mandatory field
                "width": 300,
                "height": 50
            }
        },
        {
            ## font
            "id": "your_font_id", ## mandatory field
            "type": "font", ## mandatory field
            "values": { ## mandatory field
                "fontName": "Times New Roman", ## mandatory field
                "fontSize": 13, ## mandatory field
                "fontStyle": "bold" ## default: plain, optional field
            }
        }
    ],
    ## the part for defining panel, where each object of panel array will be converted into JPanel.class
    "panels": [
        {
            "id": "some_panel_id", ## mandatory field
            "scope": "prototype", ## default: singleton (one instance per application), has the same meaning as in Spring DI, optional field
            ## optional object, a map with custom properties for this panel, which can be retrieved as a part of PropertiesMap
            "properties": {
                "start_value": 14,
                "internal_id": "3aff"
            },
            "styles": [ ## styles binding, optional field
                "your_background_id",
                "your_foreground_id",
                ## "your_size_id",
                "your_font_id"
            ],
            ## the part for defining elements, where each object of element array will be converted into JComponent.class
            "elements": [
                ## control button
                {
                    "id": "an_id_0", ## mandatory field
                    "type": "controlButton", ## mandatory field
                    "follow": false, ## property for element layout ('row' -> lay after the last element, to X-axis), default: false, optional field
                    "title": "a control button", ## optional field
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## label
                {
                    "id": "an_id_1", ## mandatory field
                    "type": "label", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a label", ## optional field
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## content button
                {
                    "id": "an_id_2", ## mandatory field
                    "type": "contentButton", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a content button", ## optional field
                    ## for content button 'properties' are reserved for Element API logic, where by key 'selected' must be defined 'positive value', by 'deselected' - 'negative value', optional field
                    ## default: {"selected": "true", "deselected": "false", "preselected": "false"}
                    ## example of reading state of selected content button - {"an_id_2": "true"}
                    "properties": {
                        "preselected": "false",
                        "selected": "true",
                        "deselected": "false"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## check box
                {
                    "id": "an_id_3", ## mandatory field
                    "type": "checkBox", ##mandatory field
                    "follow": false, ## optional field
                    "title": "a check box", ## optional field
                    "properties": { ## the same logic as for 'content button', optional field
                        "preselected": "true",
                        "selected": "true",
                        "deselected": "false"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## text field
                {
                    "id": "an_id_4", ## mandatory field
                    "type": "textField", ## mandatory field
                    "follow": false, ## optional field
                    ## for text field 'properties' are reserved for Element API logic, where by key 'defaultValue' can be defined default value for this text field, optional field
                    "properties": {
                        "defaultValue": "your default value"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## text area
                {
                    "id": "an_id_5", ## mandatory field
                    "type": "textArea", ## mandatory field
                    "follow": false, ## optional field
                    "properties": { ## the same logic as for 'text area', optional field
                        "defaultValue": "your default value"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## slider
                {
                    "id": "an_id_6", ## mandatory field
                    "type": "slider", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a slider", ## optional field
                    ## for slider 'properties' are reserved for Element API logic, where by key 'vertical' can be defined slider orientation, optional field
                    ## true - vertical orientation, false - horizontal orientation, default: false
                    "properties": {
                        "vertical": false
                    },
                    ## key-value pairs for its component, where key is for business logic, value for view, optional field
                    "values": {
                        "level_1": "1",
                        "level_2": "2",
                        "level_3": "3",
                        "level_4": "4",
                        "off": "off"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## single selection combo box
                {
                    "id": "an_id_7", ## mandatory field
                    "type": "comboBox", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a combo box", ## optional field
                    "values": { ## the same logic as for 'slider', optional field
                        "val_1": "Value 1",
                        "val_2": "Value 2",
                        "val_3": "Value 3",
                        "val_4": "Value 4",
                        "abc": "Abc"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## multi selection combo box
                {
                    "id": "an_id_8", ## mandatory field
                    "type": "multiComboBox", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a multi combo box", ## optional field
                    ## for multi combo box 'properties' are reserved for Element API logic, where by key 'listStyle' can be defined states-array-to-string function, optional field
                    ## linear (default) -> "val_1,val_2,val_3", jsonArray -> ["val_1", "val_2", "val_3"]
                    "properties": {
                            "listStyle": "jsonArray"
                        },
                    "values": { ## optional field
                        "val_1": "Value 1",
                        "val_2": "Value 2",
                        "val_3": "Value 3",
                        "val_4": "Value 4",
                        "abc": "Abc"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## spinner
                {
                    "id": "an_id_9", ## mandatory field
                    "type": "spinner", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a spinner", ## optional field
                    "values": { ## the same logic as for 'slider', optional field
                        "val_1": "Value 1",
                        "val_2": "Value 2",
                        "val_3": "Value 3",
                        "val_4": "Value 4",
                        "abc": "Abc"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                },
                ## radio box
                {
                    "id": "an_id_10", ## mandatory field
                    "type": "radioBox", ## mandatory field
                    "follow": false, ## optional field
                    "title": "a radio box", ## optional field
                    "values": { ## the same logic as for 'slider', optional field
                        "val_1": "Value 1",
                        "val_2": "Value 2",
                        "val_3": "Value 3",
                        "val_4": "Value 4",
                        "abc": "Abc"
                    },
                    "styles": [ ## optional field
                        "your_background_id",
                        "your_foreground_id",
                        "your_size_id",
                        "your_font_id"
                    ]
                }
            ]
        }
    ]
}
``````
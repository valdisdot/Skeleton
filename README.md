# com.valdisdot.util Library

The library contains useful classes and methods which make developing with legacy Java classes ***much*** easier.

## Structure

### package **`com.valdisdot.util.data` and class `DataCell<D>`**

`data` package contains the central of the logic class `DataCell<D>` that is a container for set/get functions. It
creates a separate level of abstraction.

``````
 JTextArea textArea = new JTextArea();
 DataCell<String> dc = new DataCell<String>(textArea::getText, textArea::setText);
 String data = dc.getData();
 dc.setData("");
 ``````

 ``````
 Map<String, Integer> map = new HashMap<>(Map.of("some_key", 1, "other_key", 9876));
 DataCell<String> dc = new DataCell<>(
    () -> map.get("some_key"),
    data -> map.put("some_key", data)
);
 Integer data = dc.getData();
 dc.setData(data * 10);
 ``````

The package also contains two internal packages - `data.element` and `data.controller`.

#### package `data.element`

- `data.element` contains three classes:

    - `DataCellGroup<D>` is a holder for a map of pairs of element logical name (key) "elem_name" and its DataCell. This
      class helps to operate data on-demand.
    - `Element<D, C>` is an interface of the view-data bridge. It operates data with type D and supplies a view of type
      C (extends *Supplier<C>*). Default implementation: `JElement<D>` from `com.valdisdot.ui.gui.element`.
    - `ElementGroup<D>` extends DataCellGroup<D>. The class itself is an implementation with boilerplate for getting
      DataCell<D> from the Element<D, ?>.

#### package `data.controller`

- `data.controller` contains implementations of interface Runnable, which are ready-to-use set of classes for operating
  the DataCellGroups by different ways.

``````
DataCellGroup<String> dataCellGroup = new DataCellGroup<>(
    Map.of(
        "cell1", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
        "cell2", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
        "cell3", new DataCell<>(UUID.randomUUID()::toString, (s) -> {})
    )
);
Runnable dataController = new RawDataController<>(
    dataCellGroup,
    map -> System.out.println(ValuesParser.toJSONObject(map))
);
dataController.run();
``````

``````
{
	"cell2": "a38adccf-60f6-4862-babb-c7f16c80cc2f",
	"cell3": "e9db3776-581c-4244-a6f8-8ec203e6c396",
	"cell1": "315344a5-4e56-4399-8480-d4e1cbff2392"
}
``````

**Set of controllers:**

1. `AtomicResetDataController<D>`

``````
Runnable atomicResetDataController = new AtomicResetDataController<>(
   dataCellGroup,
   cellName -> cellName.equals("temperature_2") ? 20 : 30
);
``````

2. `BulkResetDataController<>`

``````
Runnable bulkResetController = new BulkResetDataController<>(
   dataCellGroup,
   25
);
``````

3. `ConvertingDataController<>`

``````
Runnable convertingDataController = new ConvertingDataController<>(
   dataCellGroup,
   ValuesParser::toJSON,
   System.out::println
);
``````

4. `RawDataController<>`

``````
Runnable rawDataController = new RawDataController<>(
   dataCellGroup,
   (dataMap) -> System.out.println(
      dataMap.values().stream()
         .mapToInt(i -> i)
         .average()
         .orElse(0.0))
);
``````

### package **`com.valdisdot.util.tool`**

The package itself contains util-classes for simple and monad operations like simple parsing or converting.

`ValuesParser` class has simple method for parsing values. For instance:

- `int toInt(String value, int defaultValue)`
- `int fromHEXToDecimalInt(String value, int defaultValue)`
- `String toJSONArray(Collection<?> collection)`
- `String toJSONObject(Map<?,?> map)`

### package **`com.valdisdot.util.ui`**

The package contains class `SimpleUI` and internal package `ui.gui`.

#### class **`SimpleUI`**

**SimpleUI** is a simple UI util. The main purpose of the class is to simplify creating a CLI. It uses any InputStream
and OutputStream for interacting with user.

The instance of the class can be created with a constructor with params or through the `SimpleUI.Builder`. Notice, that
class **uses Thread.sleep()**, so it implements Runnable for using with a separate Thread.

``````
SimpleUI cli = SimpleUI.builder()
   .name("UICodeExample CLI")
   .helloMessage(null)
   .inputInvitationMessage(null)
   .commandMap(
        Map.of(
            "test1", () -> "HEY FROM test1",
            "test2", () -> {
                    throw new Exception("EXCEPTION FROM test2");
                }
        )
    )
    .unknownCommandAction(() -> "unknown command")
    .exitCommand(
        "exit",
        () -> "closing ..."
    )
    .helpCommand(
        "help",
        null
    )
    .exceptionHandler(Throwable::getMessage)
    .inputStream(System.in)
    .outputStream(System.out)
    .build();
new Thread(cli).start();
``````

#### package **`ui.gui`**

**ui.gui** package contains utilities for manual create or parsing GUI (to `javax.swing`)

##### package **`ui.gui.mold`**

The central logic unit is the 'molds' from `ui.gui.mold`. For a single element (label, text field, check box etc.) it is
an `ElementMold`, for a panel - `PanelMold`, for a frame - `FrameMold`, for the whole application it is
a `ApplicationMold`.
Each mold contains its own properties and information for about GUI. Molds can be created by hands or parsed from
outside (from JSON or XML files).

Handwriting the code can be overwhelming:

``````
FrameMold frameMold = new FrameMold("frame_id", "My frame!");
frameMold.addPanelMold(
   new PanelMold()
      .add(new ElementMold("label", "Some label 1", "label1", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
      .addToRow(new ElementMold("label", "Some label 2", "label2", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
      .addToRow(new ElementMold("label", "Some label 3", "label3", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
      .addToRow(new ElementMold("label", "Some label 4", "label4", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
      .add(new ElementMold("label", "Some label 5", "label5", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
      .add(new ElementMold("label", "Some label 6", "label6", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
);
ApplicationMold applicationMold = new ApplicationMold();
applicationMold.setApplicationName("My application");
applicationMold.setBuildingPolicy(ApplicationMold.FramesGrouping.PECULIAR_FRAME);
applicationMold.addFrameMold(frameMold);
``````

##### package **`ui.gui.element`** and class **`JElement`**

Package `ui.gui.element` contains abstract implementation of `Element<D, C>` - `JElement<D>`.
The class *JElement* returns logical name (key) of the Element, a JComponent as a Supplier of JComponent (representation
of view) and a DataCell of its Element.

*Implementations of JElement:*

- `CheckBox` is an implementation for **JCheckBox**.

``````
CheckBox checkBox = new CheckBox("is_checked", "Is checked?", false, "sure", "nope");
``````

``````
JCheckBox jCheckBox = new JCheckBox("Is checked?");
jCheckBox.setSelected(false);
CheckBox checkBox = new CheckBox("is_checked", jCheckBox, "sure", "nope");
``````

- `ComboList` is an implementation for **JComboBox**.

``````
ComboList comboList = new ComboList(
   "favourite_color", 
   List.of("white", "red", "blue", "green", "other"),
   (currentValue, values) -> {
      String lastValue = values.get(values.size() - 1);
      return currentValue.equals(lastValue) ? 0 : values.size() - 1;
   }
);
``````

``````
JComboBox<String> comboBox = new JComboBox<>(new Vector<>(List.of("white", "red", "blue", "green", "other")));
ComboList comboList = new ComboList(
   "favourite_color",
   comboBox,
   null
);
``````

- `ContentButton` is an implementation for **JButton**. The class uses the same logic as CheckBox class.

``````
JButton button = new JButton("Accept");
ContentButton contentButton = new ContentButton("accepted", button, "true", "false");
``````

- `ModifiableLabel` is an implementation for **JLabel**. The class is useful for cases where JLabel has to be changed
  on-demand.

``````
JLabel label = new JLabel("0");
ModifiableLabel modifiableLabel = new ModifiableLabel("counter", label);
``````

- `MultiList<D>` is an implementation for **JList**.

``````
JList<String> jList = new JList<>(new String[]{"rates", "deposit", "registration", "loans"});
MultiList<String> multiList = new MultiList<>("request_type", jList);
``````

- `RadioButtons` is an implementation for a group of **JRadioButtons**.

``````
LinkedHashMap<JRadioButton, String> orderedMap = new LinkedHashMap<>();
orderedMap.put(new JRadioButton("Happy", true), "3");
orderedMap.put(new JRadioButton("Neutral", false), "2");
orderedMap.put(new JRadioButton("Sad", false), "1");
RadioButtons radioButtons = new RadioButtons("customer_happiness", orderedMap);
``````

- `TextInputElement` is an implementation for **JTextArea** and **JTextField**.

``````
TextInputElement textInputElement = new TextInputElement("customer_phone");
``````

``````
JTextField textField = new JTextField();
textField.setFont(new Font("Arial", Font.BOLD, 13));
TextInputElement textInputElement = new TextInputElement("customer_phone", textField);
``````

- `ScrollableTextArea` is an implementation for **JTextArea**. The class wraps a JTextArea into a JScrollPane

``````
JTextArea textArea = new JTextArea();
textArea.setWrapStyleWord(true);
textArea.setLineWrap(true);
ScrollableTextArea scrollableTextArea = new ScrollableTextArea("comment", textArea);
``````

- `Slider<D>` is an implementation for **JSlider**.

``````
LinkedHashMap<String, String> orderedMap = new LinkedHashMap<>();
orderedMap.put("Happy", "3");
orderedMap.put("Neutral", "2");
orderedMap.put("Sad", "1");
Slider<String> slider = new Slider<>("customer_happiness", orderedMap);
``````

- `Spinner` is an implementation for **JSpinner**.

``````
Spinner spinner = new Spinner("rate", Spinner.asNumberRange(1, 5, 1));
``````

``````
JSpinner jSpinner = new JSpinner();
jSpinner.setPreferredSize(new Dimension(150, 30));
Spinner spinner = new Spinner("rate", jSpinner, Spinner.asList(List.of("good", "neutral", "bad")));
``````

- `WrappedDataElement<D, C>` is a common implementation. It is useful for cases, where converting is required.

``````
Spinner spinner = new Spinner("rate", Spinner.asNumberRange(1, 5, 1));
WrappedDataElement<String, Integer> wrappedElement = new WrappedDataElement<>(
   spinner, 
   Integer::parseInt, 
   String::valueOf
);
int data = wrappedElement.getDataCell().getData();
``````

##### package **`ui.gui.component`**

The package contains classes for GUI, which are not part of gui.element package, but are very useful for parsing and
composing GUI.

1. enum `ComponentType` contains enumeration of tags, which can be used for parsing logic.

For example, the values of the enums is used for logic of parsing gui from JSON
file (`ui.gui.parser.json.JsonGUIParser`).

``````
CHECK_BOX("checkBox"),
CONTENT_BUTTON("contentButton"),
CONTROL_BUTTON("button"),
LABEL("label"),
LIST("list"),
MULTI_LIST("multiList"),
MODIFIABLE_LABEL("modifiableLabel"),
RADIO_BUTTONS("radioButtons"),
SLIDER("slider"),
SPINNER("spinner"),
TEXT_AREA("textArea"),
TEXT_FIELD("textField")
``````

2. class `ControlButton` obviously is used as a control button and reduce some boilerplate. It accepts button logical
   name (key), `data.controller.DataController` and either **label for JButton** or **JButton itself**.
3. class `Frame` *extends JFrame* and represents a frame with a menu bar. Each JPanel from 'menuItems' map will be
   parsed as a menu item. This type of frame will be created if frame building policy `ApplicationMold.FramesGrouping`
   equals `MENU("menu")`.
4. class `JComponentDecorator<<C extends JComponent>` is a supplier of C, where C extends JComponent. It is a util
   class, the builder for decorate JElements with colors, size and font and is used for parsing.

##### package **`ui.gui.tool`**

The package contains util-classes for monad operations with GUI like reversing color or calculating darkness of a color.

##### package **`ui.gui.parser`**

The package contains the parsing interface `MoldParser<P extends JPanel>`, its default
implementation `DefaultMoldParser` and package with logic for parsing 'gui molds' from json `parser.json`.

1. interface `MoldParser<P extends JPanel>`

The interface is a contract for parsers. An implementation must contain two methods:

- `DataCellGroup<String> getDataCellGroups()` for controlling the data.
- `Map<String, Consumer<ActionListener>> getButtonsActionListenerConsumers()` for binging action with control button by
  its name (key).

2. class `DefaultMoldParser`

Default implementation of MoldParser, it has three constructors:

- for cases, where we have more implementation of JElement than are presented in
  package `ui.gui.element`. `listToStringFunction` is parsing function for cases, where *DataCell operates with a list
  of data*.

``````
  DefaultMoldParser(
      String rootPanelName,
      Iterable<PanelMold> panelMolds,
      Function<ElementMold, JElement<String>> customParseFunction,
      Function<List<String>, String> listToStringFunction
  )
``````

- constructor, where `listToStringFunction` is `tool.ValuesParser::toJSON`.

``````
   DefaultMoldParser(
       String rootPanelName, 
       Iterable<PanelMold> panelMolds, 
       Function<List<String>, String> listToStringFunction
)
``````

- for parsing from a `FrameMold`.

``````
   DefaultMoldParser(
       FrameMold frameMold, 
       Function<List<String>, 
       String> listToStringFunction
)
``````

3. package `parser.json`

The public class of the package is `JsonApplicationMoldParser`. The class returns `ApplicationMold` and uses
*jackson.ObjectMapper* util for parsing *json* into package-level class `JsonApplicationPlot`.

`JsonApplicationMoldParser` has four constructors:

- `JsonApplicationMoldParser(File jsonFile) throws IOException`
- `JsonApplicationMoldParser(String json) throws JsonProcessingException`
- `JsonApplicationMoldParser(InputStream jsonInputStream) throws IOException`
- `JsonApplicationMoldParser(URI uriToJson) throws IOException`

1. *JSON file properties and structure:*

`applicationName` - string, name of an application (the value will be used for a one-frame application).

`framesGrouping` - string, one of the values of enum ApplicationMold.FramesGrouping: `menu`, `joint`, `peculiar` (default for unknown or null/value absence).

`itemMenuName` - string, menu name in the application frame, for case `"framesGrouping" = "menu"`. `"itemMenuName" = "applicationName"` as default for null/value absence.

`menuBackground` - string (HEX-color), menu color, for case `"framesGrouping" = "menu"`. Default is gray.

`properties` - object, the holder for properties like size, color, font, for all elements in an application. Structure:

- `sizes` - array of objects, contain property name and dimension. Structure of the internal elements:
- - `name` - string, property name.
- - `width` - integer.
- - `height` - integer.

- `colors` - array of objects, contain property name, value of the color and color type. Structure of the internal elements:
- - `name` - string, property name.
- - `value` - string (HEX-color).
- - `type` - string, type of color usage, either `background` or `foreground`.

- `fonts` - array of objects, contains property name, font name, font size and font style. Structure of the internal elements:

- - `name` - string, property name.
- - `fontName` - string, font family or font name like *Arial*.
- - `fontSize` - integer.
- - `fontStyle` - string, font style like `bold`, `italic` or `plain` (default for unknown or null/value absence).

`frames` - array of objects, contain application logical frames. The current parsing depends on value `framesGrouping`. Structure of the internal elements:

- - `name` - string, logical name of the frame. The value is used for `JPanel.setName` or `JFrame.setName`.
- - `title` - string, a title for a frame. It is used for `JFrame.setTitle` for each frame if `"framesGrouping"="peculiar"`
- - `rootBackground` - string (HEX-color), set the background of a frame's panel.
- - `panels` - array of objects, contain separate logical panels of the frame. The placing of each panel depends on this value. Structure of the internal elements:

- - - `fromTheTopOfFrame` - boolean, the placing policy of the panel. The placing on the current frame depends on this value. If `true` - the panel will be placed *from the top*, else the panel will be place *under the last panel*. Default is `true`.
- - - `properties` - array of strings, contains the name of defined properties in the root element. If properties for an element of the panel *is not specified* - this value *will be applied*.
- - - `elements` - array of objects, contains elements themselves. Structure of the internal elements:

- - - -  `type` - string, **required**, the type of element. See bellow (***list, element types***)
- - - -  `name` - string, **required**, logical name of the frame. The value is used for `JComponent.setName`.
- - - -  `title` - string, *title side* of the element, the text that is displayed on the element. **Required** for `checkBox`, `contentButton`, `button`, `label`, `modifiableLabel`.
- - - -  `laying` - string or absence, defines element *laying policy*. If equals `newRow` - element will be placed from new line and begin a new row, if `row` - element will be placed in the previous row or will begin a new row if previous *laying policy* isn't defined. *In other cases* element is placing *from a new line*. 
- - - -  `properties` - array of strings, contains the name of defined properties in the root element. Will override properties of the parent element (aka CSS).
- - - -  `values` - array of string. The values holder, that is specified for each `type`. **Required**  for `checkBox`, `contentButton`, `multiList`, `list`, `radioButtons`, `slider` and `spinner`.

2. *`element` types:*

- `checkBox` - represents a checkbox. Property `values` must be presented and contains data. The fist element of the list represent *true* (check box is selected), the second is *false* (check box is not selected). Minimum size is 2.

``````
{
  "type": "checkBox",
  "name": "customer_agreement",
  "title": "I accept company's policy",
  "properties": ["foregrond_extra_red"],
  "values": ["signed", "declined"]
}
``````

`contentButton` - represents a data button. Property `values` must be presented and contains data. The fist element of the list represent *true* (button is clicked), the second is *false* (button is not clicked). Minimum size is 2.

``````
{
  "type": "contentButton",
  "name": "fast_shipping",
  "title": "Add 7.99$ for fast shipping",
  "properties": ["nice_button_color", "white_foreground", "pretty_content_font"],
  "values": ["true", "false"]
}
``````

`button` - represents a control button. The `name` value is used for subscribe `ActionListner` or `Runnable` for this button.

``````
{
  "type": "button",
  "name": "connect_to_server",
  "title": "Connect",
  "properties": ["control_color", "control_font"]
}
``````

`label` - represents a label. It can be used as description for an element which doesn't have a label text like `textArea`.

``````
{
  "type": "label",
  "title": "Type your name: ",
  "laying": "newRow",
  "properties": ["lite_cyan_foreground"]
}
``````

`list` - represent a combo box, where we can select only one element. Property `values` must be presented and contains items for a list. Minimum size is 1. 

``````
{
  "type": "list",
  "name": "mode",
  "laying": "row",
  "properties": ["nice_button_color", "white_foreground", "pretty_content_font"],
  "values": ["easy", "normal", "extreeme"]
}
``````

`multiList` - represent a list with multi-selection. Property `values` must be presented and contains items for a list. Minimum size is 1.

``````
{
  "type": "multiList",
  "name": "request_themes",
  "laying": "row",
  "properties": ["nice_button_color", "white_foreground", "pretty_content_font"],
  "values": ["rates", "deposit", "creadit", "registration"]
}
``````

`modifiableLabel` - represent a label with a variable text, which can be changed in runtime.

``````
{
  "type": "modifiableLabel",
  "name": "hello_message",
  "title": "Hello, {$NAME}"
}
``````

`radioButtons` - represents a group of radiobutton. Property `values` must be presented and contains pairs ***label - data*** (like *Map.of()*). Minimum size is 2.

``````
{
  "type": "radioButtons",
  "name": "login_mode",
  "properties": ["control_color", "control_font"],
  "values": ["as superuser", "su", "as user", "user"]
}
``````

`slider` - represents a slider (ranged). Property `values` must be presented and contains pairs ***label - data*** (like *Map.of()*). Minimum size is 2.

``````
{
  "type": "slider",
  "name": "temperature_max_preset",
  "properties": ["control_color", "control_font"],
  "values": [
    "guest room", "22",
    "living room", "23",
    "bathroom", "20"
    ]
}
``````

`spinner` - represents a numbers' spinner. Property `values` must be presented and contains three values: **minimum value**, **maximum value** (in any order) and the last one is **step**. Minimum size is 3.

``````
{
  "type": "spinner",
  "name": "temperature",
  "properties": ["control_color", "control_font"],
  "values": ["90", "40", "5"]
}
``````

`textArea` - represents a text area.

``````
{
  "type": "textArea",
  "name": "comment",
  "properties": ["text_input_color", "content_font"]
}
``````

`textField` - represents a text field (one row).

``````
{
  "type": "textField",
  "name": "customer_phone",
  "properties": ["text_input_color", "content_font"]
}
``````

##### class **`ui.gui.Control`**

It is a data class for holding `DataCellGroup<String, Map<String, String>` and `Map<String, Consumer<ActionListener>>`
for control buttons.
Except for holding control under ... control, the class also simplifies some operation with DataCellGroup (delegate) and
binds ActionListeners with control buttons. We can make it through the delegation or get DataCellGroup and Map of
ActionListeners.

##### class **`ui.gui.GUI`**

The class is *user-end class* for parsing GUI.

It accepts an ApplicationMold. We can use ready-to-use ApplicationMolds
provider `ui.gui.parser.json.JsonApplicationMoldParser` from some JSON file or build by hands `ApplicationMold`.

`ApplicationMold.FramesGrouping` defines the logic for building a graphical interface.

- `MENU` -> `GUI` will create a single JFrame (`ui.gui.component.Frame`) with JMenu, all frames will be placed as
  JPanels.

- `PECULIAR_FRAME` -> `GUI` will create a single JFrame, all frames will be places in a JScrollPane.

- `JOINT_FRAME` *(default)* -> `GUI` will create a linked list of JFrames.

Two constructors are provided:

- `GUI(ApplicationMold applicationMold, Function<List<String>, String> listToStringFunction)`,
  where `listToStringFunction` defines logic of parsing list of values from `DataCell<? extends List<String>>` into
  single String.

- `GUI(ApplicationMold applicationMold)`, `listToStringFunction` will be defined as `ValuesParser::toJSON` by default.

**GUI class API**:

- `String getApplicationTitle()`
- `JFrame getFirstFrame()` return the linkedList.getFirst(), method is useful for cases with a single JFrame.
- `List<JFrame> getFrames()`
- `LinkedHashMap<String, JPanel> getFrameTitlePanelViewMap()` returns ordered map where *key is **frame title*** and
  *value is parsed FrameMold into JPanel*. Method is useful for scenario where users want to compose frames by
  themselves.
- `Map<String, Control> getControlMap()` return the map where *key is **frame name*** and *value is a controllable data
  elements* of the frame.

## Examples

### Example of using class GUI for parsing JSON from the `sketch/gui.json`

*Plot*: [gui.json file](https://github.com/valdisdot/Utilities/blob/main/sketch/gui.json)

*Java code*:

``````
        //parse gui.json
        GUI gui = new GUI(new JsonApplicationMoldParser(new File("sketch/gui.json")).getApplicationMold());
        
        //get control
        Map<String, Control> controlMap = gui.getControlMap();
        Control customerRequestWindowsControl = controlMap.get("customer_request_window");
        Control monitorWindowsControl = controlMap.get("monitor");
        
        //fill with some business logic
        //1) get data consumer from 'monitor' - 'monitor_area'
        Consumer<String> dataConsumer = new Consumer<String>() {
            StringBuilder builder = new StringBuilder();
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell(
                        "monitor_area",
                        builder.append(s).append("\n\n").toString());
            }
        };
        
        //2) get data consumer from 'monitor' - 'counter'
        Consumer<String> counter = new Consumer<String>() {
            int counter = 0;
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell("counter", String.valueOf(++counter));
            }
        };
        
        //3) define get data controller
        Runnable sendToMonitorController = new ConvertingDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ValuesParser::toJSON,
                dataConsumer.andThen( //send data to monitor
                        counter //and then increase counter (which ignores data)
                )
        );
        
        //4) define print data controller
        Runnable printController = new RawDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                System.out::println
        );
        
        //5) define clean data controller
        Runnable resetController = new BulkResetDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ""
        );
        
        //bind action listeners with buttons
        customerRequestWindowsControl.bindActionForButton("send", sendToMonitorController);
        customerRequestWindowsControl.bindActionForButton("print", printController);
        customerRequestWindowsControl.bindActionForButton("clean", resetController);
        
        //show view
        gui.getFrames().stream().forEach(frame -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });
``````

*View* :

![view example](https://github.com/valdisdot/Utilities/blob/main/sketch/gui_view.png?raw=true)










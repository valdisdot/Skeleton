# com.valdisdot.util Library

The library contains useful classes and methods which make developing with legacy Java classes ***much*** easier.
## Structure

### package **`com.valdisdot.util.data`**

`data` package contains the central of the logic class `DataCell<D>` that is a container for set/get functions. It creates a separate level of abstraction.
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
 - `data.element` contains three classes:

    - `DataCellGroup<D>` is a holder for a map of logical name pairs: "elem_name" and its DataCell. This class helps to operate with data on-demand.
    - `Element<D, C>` is an interface of the view-data bridge. It operates with data D and supplies a data source of type C. Extends *Supplier<C>*. Typical implementation: `JElement<D>` from `com.valdisdot.ui.gui.element`.
    - `ElementGroup<D>` extends DataCellGroup<D>. The class itself is an implementation with boilerplate for getting DataCell<D> from the Element<D, ?>.
- `data.controller` contains the interface DataController with a simple method `void process()` and its ready-to-use implementations for operating the DataCellGroups by different ways.
``````
DataCellGroup<String> dataCellGroup = new DataCellGroup<>(
    Map.of(
        "cell1", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
        "cell2", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
        "cell3", new DataCell<>(UUID.randomUUID()::toString, (s) -> {})
    )
);
DataController dataController = new RawDataController<>(
    dataCellGroup,
    map -> System.out.println(ValuesParser.toJSONObject(map))
);
dataController.process();
``````
``````
{
	"cell2": "a38adccf-60f6-4862-babb-c7f16c80cc2f",
	"cell3": "e9db3776-581c-4244-a6f8-8ec203e6c396",
	"cell1": "315344a5-4e56-4399-8480-d4e1cbff2392"
}
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

1. **SimpleUI** is a simple UI util. The main purpose of the class is to simplify creating a CLI. It uses any InputStream and OutputStream for interacting with user.

The instance of the class can be created with a constructor with params or through the `SimpleUI.Builder`. Notice, that class **uses Thread.sleep()**, so it implements Runnable for using with a separate Thread.

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
2. **ui.gui** package contains utilities for manual create or parsing GUI (to `javax.swing`)

The central logic unit is a mold from `ui.gui.mold`. For a single element (label, text field, check box etc.) it is an `ElementMold`, for a panel - `PanelMold`, for a frame - `FrameMold`, for the whole application it is a `ApplicationMold`.
Each mold contains its own properties and information for about GUI. Molds can be created by hands or parsed from outside (from JSON or XML files).

Package `ui.gui.element` contains abstract implementation of `Element<D, C>` - `JElement<D>`. 

The class *JElement* returns logical name (key) of the Element, a JComponent as a Supplier of JComponent (representation of view) and a DataCell of its Element. 
Inheritors of `JElement<D>`:
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
- `ModifiableLabel` is an implementation for **JLabel**. The class is useful for cases where JLabel has to be changed on-demand.
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
- `Slider<D>`is an implementation for **JSlider**.
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










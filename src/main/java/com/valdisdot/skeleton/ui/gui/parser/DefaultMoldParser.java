package com.valdisdot.skeleton.ui.gui.parser;

import com.valdisdot.skeleton.data.element.DataCellGroup;
import com.valdisdot.skeleton.tool.ValuesParser;
import com.valdisdot.skeleton.ui.gui.component.ComponentType;
import com.valdisdot.skeleton.ui.gui.component.ControlButton;
import com.valdisdot.skeleton.ui.gui.component.JComponentDecorator;
import com.valdisdot.skeleton.ui.gui.element.*;
import com.valdisdot.skeleton.ui.gui.mold.ElementMold;
import com.valdisdot.skeleton.ui.gui.mold.FrameMold;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

//default parser class from molds (after parsing from json, manual codding, xml etc.) to JPanels
public class DefaultMoldParser implements MoldParser<JPanel> {
    private final Function<ElementMold, JElement<String>> customParseFunction;
    private final Function<List<String>, String> listToStringFunction;
    private DataCellGroup<String> dataCellGroup;
    private JPanel root;
    private Map<String, Consumer<ActionListener>> controlButtonsActionListenersConsumers;

    //in-app, default
    public DefaultMoldParser(){
        customParseFunction = null;
        listToStringFunction = ValuesParser::toJSON;
    }

    //for library-users
    //customParseFunction - for future JElement types (which are not present in ComponentType.enum) or for debugging
    public DefaultMoldParser(Function<ElementMold, JElement<String>> customParseFunction, Function<List<String>, String> listToStringFunction) {
        this.customParseFunction = customParseFunction;
        this.listToStringFunction = Objects.requireNonNullElse(listToStringFunction, ValuesParser::toJSON);
    }

    //wait till parsing is done
    @Override
    public synchronized void parse(FrameMold frameMold){
        dataCellGroup = new DataCellGroup<>();
        controlButtonsActionListenersConsumers = new HashMap<>();
        root = new JPanel(new MigLayout("insets 0,gap 0px 0px"));
        root.setName(frameMold.getName());
        frameMold.getPanelMolds().forEach(panelMold -> {
            String panelConstraint = panelMold.isFromTheTopOfFrame() ? "aligny top" : "wrap";
            JPanel currentPanel = new JPanel(new MigLayout("wrap"));
            currentPanel.setBackground(new Color(panelMold.getBackgroundColor()));
            panelMold.getMoldConstraintMap().forEach(
                    ((elementMold, constraint) -> {
                        if (ComponentType.CONTROL_BUTTON.getValue().equals(elementMold.getType())) {
                            JButton button = parseControlButton(elementMold).get();
                            currentPanel.add(button, constraint);
                            controlButtonsActionListenersConsumers.put(button.getName(), button::addActionListener);
                        } else if (ComponentType.LABEL.getValue().equals(elementMold.getType())) {
                            JLabel label = parseStaticLabel(elementMold);
                            currentPanel.add(label, constraint);
                        } else {
                            JElement<String> element = parse(elementMold, customParseFunction, listToStringFunction);
                            dataCellGroup.put(element.getName(), element.getDataCell());
                            currentPanel.add(element.get(), constraint);
                        }
                    })
            );
            root.add(currentPanel, panelConstraint);
        });
        root.setBackground(new Color(frameMold.getRootBackgroundColor()));
    }

    protected JElement<String> parse(ElementMold elementMold, Function<ElementMold, JElement<String>> customParseFunction, Function<List<String>, String> listToStringFunction) {
        //common restriction
        Objects.requireNonNull(elementMold, "Element mold is null");
        Objects.requireNonNull(elementMold.getName(), "Element mold name is null. Element: [" + elementMold + "]");
        Objects.requireNonNull(elementMold.getType(), "Element mold type is null. Element: [" + elementMold + "]");
        switch (ComponentType.fromValue(elementMold.getType())) {
            case CHECK_BOX: {
                //requires title and values >=2
                Objects.requireNonNull(elementMold.getTitle(), "Title is null. Element: [" + elementMold + "]");
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().size() < 2)
                    throw new IllegalArgumentException("Values size is less than 2. Element: [" + elementMold + "]");
                JCheckBox checkBox = new JComponentDecorator<>(new JCheckBox(elementMold.getTitle()))
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new CheckBox(elementMold.getName(), checkBox, elementMold.getFirstValue(), elementMold.getSecondValue());
            }
            case CONTENT_BUTTON: {
                //requires title and values >=2
                Objects.requireNonNull(elementMold.getTitle(), "Title is null. Element: [" + elementMold + "]");
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().size() < 2)
                    throw new IllegalArgumentException("Values size is less than 2. Element: [" + elementMold + "]");
                JButton button = new JComponentDecorator<>(new JButton(elementMold.getTitle()))
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new ContentButton(elementMold.getName(), button, elementMold.getFirstValue(), elementMold.getSecondValue());
            }
            case MODIFIABLE_LABEL: {
                //requires title
                Objects.requireNonNull(elementMold.getTitle(), "Title is null. Element: [" + elementMold + "]");
                JLabel label = new JComponentDecorator<>(new JLabel(elementMold.getTitle()))
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new ModifiableLabel(elementMold.getName(), label);
            }
            case MULTI_LIST: {
                //requires values >=1
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().isEmpty())
                    throw new IllegalArgumentException("Values is empty. Element: [" + elementMold + "]");
                JList<String> jList = new JComponentDecorator<>(new JList<>(new Vector<>(elementMold.getValues())))
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                JElement<String> wrapped = new WrappedDataElement<>(
                        new MultiList<>(elementMold.getName(), jList),
                        listToStringFunction,
                        string -> List.of()
                );
                new JComponentDecorator<>(wrapped.get()).preferredSize(elementMold.getWidth(), elementMold.getHeight()).get();
                return wrapped;
            }
            case LIST: {
                //requires values >=1
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().isEmpty())
                    throw new IllegalArgumentException("Values is empty. Element: [" + elementMold + "]");
                JComboBox<String> comboBox = new JComponentDecorator<>(new JComboBox<>(new Vector<>(elementMold.getValues())))
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new ComboList(elementMold.getName(), comboBox, (name, items) -> 0); //just set current selected element to 0
            }
            case RADIO_BUTTONS: {
                //requires values >=2
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().size() < 2)
                    throw new IllegalArgumentException("Values size is less than 2. Element: [" + elementMold + "]");
                List<String> rawValues = elementMold.getValues();

                LinkedHashMap<JRadioButton, String> radioButtonValueMap = new LinkedHashMap<>();
                for (int i = 1; i < rawValues.size(); i = i + 2) {
                    JRadioButton radioButton = new JComponentDecorator<>(new JRadioButton(rawValues.get(i - 1)))
                            .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                            .background(elementMold.getBackgroundColor())
                            .foreground(elementMold.getForegroundColor())
                            .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                            .get();
                    radioButtonValueMap.put(radioButton, rawValues.get(i));
                }

                return new RadioButtons(elementMold.getName(), radioButtonValueMap);
            }
            case SLIDER: {
                //requires values >=2
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().size() < 2)
                    throw new IllegalArgumentException("Values size is less than 2. Element: [" + elementMold + "]");
                List<String> rawValues = elementMold.getValues();
                //fill ordered map where i - 1 is label, i is data value
                LinkedHashMap<String, String> labelDataValues = new LinkedHashMap<>();
                for (int i = 1; i < rawValues.size(); i = i + 2)
                    labelDataValues.put(rawValues.get(i - 1), rawValues.get(i));

                Slider<String> slider = new Slider<>(elementMold.getName(), labelDataValues);
                new JComponentDecorator<>(slider.get()) //the instance of JSlider are already initialized, all changes with JComponentDecorator will be saved
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return slider;
            }
            case SPINNER: {
                //requires values >=3
                Objects.requireNonNull(elementMold.getValues(), "Values is null. Element: [" + elementMold + "]");
                if (elementMold.getValues().size() < 3)
                    throw new IllegalArgumentException("Values size is less than 3. Element: [" + elementMold + "]");
                JSpinner jSpinner = new JComponentDecorator<>(new JSpinner())
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new Spinner(
                        elementMold.getName(),
                        jSpinner,
                        Spinner.asNumberRange(
                                ValuesParser.toInt(elementMold.getValueAt(0), true, 0),
                                ValuesParser.toInt(elementMold.getValueAt(1), true, 1),
                                ValuesParser.toInt(elementMold.getValueAt(2), true, 1)
                        )
                );
            }
            case TEXT_AREA: {
                JTextArea textArea = new JComponentDecorator<>(new JTextArea())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                ScrollableTextArea scrollableElement = new ScrollableTextArea(elementMold.getName(), textArea);
                new JComponentDecorator<>(scrollableElement.get())
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .get();
                return scrollableElement;
            }
            case TEXT_FIELD: {
                JTextField textField = new JComponentDecorator<>(new JTextField())
                        .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                        .background(elementMold.getBackgroundColor())
                        .foreground(elementMold.getForegroundColor())
                        .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                        .get();
                return new TextInputElement(elementMold.getName(), textField);
            }
            default: {
                if (Objects.nonNull(customParseFunction)) return customParseFunction.apply(elementMold);
                throw new IllegalArgumentException("Can't cast to JComponent with type: " + elementMold.getType());
            }
        }
    }

    protected ControlButton parseControlButton(ElementMold elementMold) {
        //requires title and name
        Objects.requireNonNull(elementMold.getName(), "Element mold name is null. Element: [" + elementMold + "]");
        Objects.requireNonNull(elementMold.getTitle(), "Title is null. Element: [" + elementMold + "]");
        JButton button = new JComponentDecorator<>(new JButton(elementMold.getTitle()))
                .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                .background(elementMold.getBackgroundColor())
                .foreground(elementMold.getForegroundColor())
                .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                .get();
        return new ControlButton(elementMold.getName(), button, null);
    }

    protected JLabel parseStaticLabel(ElementMold elementMold) {
        //requires title
        Objects.requireNonNull(elementMold.getTitle(), "Title is null. Element: [" + elementMold + "]");
        return new JComponentDecorator<>(new JLabel(elementMold.getTitle()))
                .preferredSize(elementMold.getWidth(), elementMold.getHeight())
                .background(elementMold.getBackgroundColor())
                .foreground(elementMold.getForegroundColor())
                .font(elementMold.getFontName(), elementMold.getFontStyle(), elementMold.getFontSize())
                .get();
    }

    @Override
    public DataCellGroup<String> getDataCellGroups() {
        return dataCellGroup;
    }

    @Override
    public Map<String, Consumer<ActionListener>> getButtonsActionListenerConsumers() {
        return controlButtonsActionListenersConsumers;
    }

    @Override
    public JPanel get() {
        return root;
    }
}

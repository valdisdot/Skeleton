package com.valdisdot.skeleton.gui.view.swing;

import com.valdisdot.skeleton.core.ViewInstance;
import com.valdisdot.skeleton.core.control.ControlUnit;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
import com.valdisdot.skeleton.core.view.PresentableUnit;
import com.valdisdot.skeleton.gui.parser.mold.ElementMold;
import com.valdisdot.skeleton.gui.parser.mold.Mold;
import com.valdisdot.skeleton.gui.parser.mold.PanelMold;
import com.valdisdot.skeleton.gui.view.swing.unit.control.ControlButton;
import com.valdisdot.skeleton.gui.view.swing.unit.presentable.*;
import com.valdisdot.skeleton.util.Functions;
import com.valdisdot.skeleton.util.PropertiesMap;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.*;

/**
 * The class represents the implementation of an abstraction between graphical view elements, their control and data exchange.
 * As the main view component operates with JPanel, internal view components are JComponents and data exchange type is simply a String.
 * @apiNote Class is open to the inheritance. All fields and essential methods have protected modifiers.
 * @since 1.0
 * @author Vladyslav Tymchenko
 * @see ViewInstance
 */
public class JView extends JElement implements ViewInstance<JPanel, JComponent, String> {
    protected final Map<String, ControlUnit<JComponent>> controlUnits;
    protected final Map<String, DataUnit<String>> dataUnits;
    protected final Map<String, PresentableUnit<JComponent>> presentableUnits;
    protected final JPanel view;
    protected final Map<String, Object> properties;

    /**
     * Instantiates a new view from a panel mold.
     * @param mold a mold
     */
    public JView(PanelMold mold) {
        Objects.requireNonNull(mold, "PanelMold is null.");
        Objects.requireNonNull(mold.getId(), "PanelMold ID is null.");
        controlUnits = new HashMap<>();
        dataUnits = new HashMap<>();
        presentableUnits = new HashMap<>();
        properties = new HashMap<>();
        JPanel view = new JPanel(new MigLayout("wrap"));
        view.setName(mold.getId());
        this.view = setComponent(view);
        //apply style and save the value into the properties
        mold.getStyles().forEach(style -> properties.put(style.getId(), this.applyStyle(style)));
        for (List<ElementMold> row : mold.getElementsOrdered()) {
            if (!row.isEmpty()) {
                view.add(parse(row.get(0)), "split " + row.size());
                for (int i = 1; i < row.size(); ++i) {
                    view.add(parse(row.get(i)), "");
                }
            }
        }
    }

    /**
     * Parses an element mold into the JComponent
     * @param mold a mold
     * @return parsed JComponent
     */
    protected JComponent parse(ElementMold mold) {
        Objects.requireNonNull(mold, "Element mold is null");
        Objects.requireNonNull(mold.getId(), "Element ID is null. Element: [" + mold + "]");
        Objects.requireNonNull(mold.getType(), "Element mold type is null. Element: [" + mold + "]");
        if (mold.isControl()) return parseButton(mold);
        else return parseJElement(mold);
    }

    /**
     * Parses an element mold into the ControlButton and returns its JComponent.
     *
     * @param mold a mold
     * @return parsed JComponent
     */
    protected JComponent parseButton(ElementMold mold) {
        ControlButton button = new ControlButton(mold.getId(), mold.getTitle());
        controlUnits.put(mold.getId(), button);
        presentableUnits.put(mold.getId(), button);
        mold.getStyles().forEach(style -> properties.put(mold.getId(), button.applyStyle(style)));
        return button.getComponent();
    }

    /**
     * Parses an element mold into {@code T extends DataUnit<String>, RepresentableUnit<JComponent>} and returns its JComponent.
     * @apiNote If the method is going to be overridden with a custom implementations of the parsing ElementMold, note that if the intention is to use also {@code super.parseJElement()}, the parent method must be called after the custom code or with a try/catch for JViewBuildException.
     * @param mold a mold
     * @return parsed JComponent
     */
    protected JComponent parseJElement(ElementMold mold) {
        if(mold.getType().equalsIgnoreCase(CheckBox.class.getSimpleName())) {
            CheckBox checkBox = new CheckBox(mold.getId(), mold.getTitle(), "true".equals(mold.getProperty("preselected")), mold.getProperty("selected"), mold.getProperty("deselected"));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), checkBox.applyStyle(style)));
            presentableUnits.put(checkBox.getId(), checkBox);
            dataUnits.put(checkBox.getId(), checkBox);
            return checkBox.getComponent();
        } else if (mold.getType().equalsIgnoreCase(ContentButton.class.getSimpleName())){
            ContentButton contentButton = new ContentButton(mold.getId(), mold.getTitle(), "true".equals(mold.getProperty("preselected")), mold.getProperty("selected"), mold.getProperty("deselected"));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), contentButton.applyStyle(style)));
            presentableUnits.put(contentButton.getId(), contentButton);
            dataUnits.put(contentButton.getId(), contentButton);
            return contentButton.getComponent();
        } else if (mold.getType().equalsIgnoreCase(Label.class.getSimpleName())){
            Label label = new Label(mold.getId(), mold.getTitle());
            mold.getStyles().forEach(style -> properties.put(mold.getId(), label.applyStyle(style)));
            presentableUnits.put(label.getId(), label);
            dataUnits.put(label.getId(), label);
            return label.getComponent();
        } else if (mold.getType().equalsIgnoreCase(MultiComboBox.class.getSimpleName())) {
            MultiComboBox multiComboBox = new MultiComboBox(mold.getId(), PresentablePair.fromMap(mold.getValues()), "jsonArray".equals(mold.getProperty("listStyle")) ? Functions.collectionToJsonArray() : Functions.collectionToString());
            mold.getStyles().forEach(style -> properties.put(mold.getId(), multiComboBox.applyStyle(style)));
            presentableUnits.put(multiComboBox.getId(), multiComboBox);
            dataUnits.put(multiComboBox.getId(), multiComboBox);
            return multiComboBox.getComponent();
        } else if (mold.getType().equalsIgnoreCase(ComboBox.class.getSimpleName())){
            ComboBox comboBox = new ComboBox(mold.getId(), PresentablePair.fromMap(mold.getValues()));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), comboBox.applyStyle(style)));
            presentableUnits.put(comboBox.getId(), comboBox);
            dataUnits.put(comboBox.getId(), comboBox);
            return comboBox.getComponent();
        } else if (mold.getType().equalsIgnoreCase(RadioBox.class.getSimpleName())){
            RadioBox radioBox = new RadioBox(mold.getId(), PresentablePair.fromMap(mold.getValues()));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), radioBox.applyStyle(style)));
            presentableUnits.put(radioBox.getId(), radioBox);
            dataUnits.put(radioBox.getId(), radioBox);
            return radioBox.getComponent();
        } else if (mold.getType().equalsIgnoreCase(Slider.class.getSimpleName())){
            Slider slider = new Slider(mold.getId(), PresentablePair.fromMap(mold.getValues()), "true".equals(mold.getProperty("vertical")));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), slider.applyStyle(style)));
            presentableUnits.put(slider.getId(), slider);
            dataUnits.put(slider.getId(), slider);
            return slider.getComponent();
        } else if (mold.getType().equalsIgnoreCase(Spinner.class.getSimpleName())){
            Spinner spinner = new Spinner(mold.getId(), PresentablePair.fromMap(mold.getValues()));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), spinner.applyStyle(style)));
            presentableUnits.put(spinner.getId(), spinner);
            dataUnits.put(spinner.getId(), spinner);
            return spinner.getComponent();
        } else if (mold.getType().equalsIgnoreCase(TextArea.class.getSimpleName())){
            TextArea textArea = new TextArea(mold.getId(), mold.getProperty("defaultValue"));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), textArea.applyStyle(style)));
            presentableUnits.put(textArea.getId(), textArea);
            dataUnits.put(textArea.getId(), textArea);
            return textArea.getComponent();
        } else if (mold.getType().equalsIgnoreCase(TextField.class.getSimpleName())) {
            TextField textField = new TextField(mold.getId(), mold.getProperty("defaultValue"));
            mold.getStyles().forEach(style -> properties.put(mold.getId(), textField.applyStyle(style)));
            presentableUnits.put(textField.getId(), textField);
            dataUnits.put(textField.getId(), textField);
            return textField.getComponent();
        }
        throw new JViewBuildException("Unknown type: " + mold.getType(), mold);
    }

    /**{@inheritDoc}*/
    @Override
    public Optional<ControlUnit<JComponent>> getControlUnit(String id) {
        return Optional.ofNullable(controlUnits.get(id));
    }

    /**{@inheritDoc}*/
    @Override
    public Map<String, ControlUnit<JComponent>> getControlUnits() {
        return Map.copyOf(controlUnits);
    }

    /**{@inheritDoc}*/
    @Override
    public Optional<DataUnit<String>> getDataUnit(String id) {
        return Optional.ofNullable(dataUnits.get(id));
    }

    /**{@inheritDoc}*/
    @Override
    public Map<String, DataUnit<String>> getDataUnits() {
        return Map.copyOf(dataUnits);
    }

    /**{@inheritDoc}*/
    @Override
    public Optional<PresentableUnit<JComponent>> getPresentableUnit(String id) {
        return Optional.ofNullable(presentableUnits.get(id));
    }

    /**{@inheritDoc}*/
    @Override
    public Map<String, PresentableUnit<JComponent>> getPresentableUnits() {
        return Map.copyOf(presentableUnits);
    }

    /**{@inheritDoc}*/
    @Override
    public JPanel getView() {
        return view;
    }

    /**{@inheritDoc}*/
    @Override
    public PropertiesMap getProperties() {
        return new PropertiesMap(properties);
    }

    /**
     * Internal RuntimeException, contains the mold which cause the throwing an exception.
     */
    public static class JViewBuildException extends RuntimeException {
        private Mold object;

        public JViewBuildException(String message, Mold object) {
            super(message);
            this.object = object;
        }

        /**
         * @return the mold which cause the throwing an exception.
         */
        public Mold getObject() {
            return object;
        }
    }
}

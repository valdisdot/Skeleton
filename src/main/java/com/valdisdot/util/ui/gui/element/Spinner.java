package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.tool.Debug;

import javax.swing.*;

public class Spinner<T extends Integer> extends AbstractElement<T> {
    @Override
    protected boolean pleaseAcceptThatYouHaveDone() {return false;}

    public Spinner() {
        JSpinner visibleSpinner = new JSpinner();
        getOnDesk(visibleSpinner);
    }

    //remove method later
    public static void getOnDesk(JComponent component) {
        //removed
        Debug.getOnTheDesk(component, 7662253);
    }

    //for JSpinner, later
    protected Integer value() {
        return T.valueOf(0);
    }
}

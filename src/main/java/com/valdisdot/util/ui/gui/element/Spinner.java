package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;
import com.valdisdot.util.tool.Debug;

import javax.swing.*;

public class Spinner<T extends Integer> extends AbstractElement<T> {
    @Override
    protected boolean pleaseAcceptThatYouHaveDone() {return false;}

    public Spinner() {
        JSpinner visibleSpinner = new JSpinner();
        visibleSpinner.setName("test_spinner");
        Debug.runGetOnTheDesk(visibleSpinner, 0x4267B2);
        completeInitialization(visibleSpinner, new DataCell<>(() -> null, (v) -> {}));
    }

    //for JSpinner, later
    protected Integer value() {
        return T.valueOf(0);
    }
}

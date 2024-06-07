package com.valdisdot.skeleton.gui;

import com.valdisdot.skeleton.OLD.ui.gui.element.MultiList;
import com.valdisdot.skeleton.FrameFactory;

import javax.swing.*;
import java.util.List;

public class MultiListCodeExample {
    public static void main(String[] args) {
        experiment1();
    }

    static void experiment1() {
        MultiList<String> multiList = new MultiList<>("test_multilist", new JList<>(new String[]{"item 1", "item 2", "item 3", "item 4"}));
        FrameFactory.playOnDesk(multiList, List.of(), System.out::println);
    }
}

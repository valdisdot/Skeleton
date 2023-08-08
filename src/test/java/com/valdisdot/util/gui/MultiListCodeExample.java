package com.valdisdot.util.gui;

import com.valdisdot.util.tool.Debug;
import com.valdisdot.util.ui.gui.element.MultiList;

import java.util.List;

public class MultiListCodeExample {
    public static void main(String[] args) {
        experiment1();
    }

    static void experiment1() {
        List<String> items = List.of("item 1", "item 2", "item 3", "item 4");
        MultiList<String> multiList = new MultiList<>("test_multilist", items);
        Debug.playOnDesk(multiList, List.of(), System.out::println);
    }
}

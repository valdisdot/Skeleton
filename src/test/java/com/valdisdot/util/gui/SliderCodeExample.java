package com.valdisdot.util.gui;

import com.valdisdot.util.ui.gui.tool.FrameFactory;
import com.valdisdot.util.ui.gui.element.Slider;

import java.util.LinkedHashMap;

public class SliderCodeExample {
    public static void main(String[] args) {
        experiment1();
        experiment2();
    }

    static void experiment1() {
        LinkedHashMap<String, String> items = new LinkedHashMap<>();
        items.put("label3", "value3");
        items.put("label1", "value1");
        items.put("label9", "value9");
        FrameFactory.playOnDesk(new Slider<>("test_slider", items), "", System.out::println);
    }

    static void experiment2() {
        LinkedHashMap<String, Integer> items = new LinkedHashMap<>();
        items.put("label3", 3);
        items.put("label1", 1);
        items.put("label9", 9);
        FrameFactory.playOnDesk(new Slider<>("test_slider", items), 0, (val) -> val.forEach((key, data) -> System.out.println("multiplied by 10 key " + key + ": " + data * 10)));
    }
}

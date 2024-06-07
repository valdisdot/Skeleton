package com.valdisdot.skeleton.OLD.ui.gui.tool;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//util class for colors
public class Colors {
    //show color option from this class, methods applying examples
    public static void displayColorInfo(Color original) {
        Color reversed = Colors.reverse(original);
        List<Color> analogousColors = Colors.analogousColors(original);
        List<Color> triadicColors = Colors.getTriadicColors(original);
        //if no GUI
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Color: " + String.format("#%02X%02X%02X", original.getRed(), original.getGreen(), original.getBlue()));
            System.out.println("Is dark: " + Colors.isDark(original.getRGB()));
            System.out.println("Reverse color: " + String.format("#%02X%02X%02X", reversed.getRed(), reversed.getGreen(), reversed.getBlue()));
            System.out.print("Analogous color: ");
            analogousColors.stream().map(color -> String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()) + " ").forEach(System.out::print);
            System.out.println();
            System.out.print("Triadic color: ");
            triadicColors.stream().map(color -> String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()) + " ").forEach(System.out::print);
        } else {
            JPanel panel = new JPanel(new MigLayout());
            panel.setBackground(Color.WHITE);
            Dimension size = new Dimension(150, 150);

            Function<String, JLabel> divLabel = JLabel::new;
            Function<Color, JLabel> colorLabel = (color) -> {
                String text = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
                JLabel label = new JLabel(text);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                return label;
            };
            panel.add(divLabel.apply("Color: " + String.format("#%02X%02X%02X", original.getRed(), original.getGreen(), original.getBlue())), "wrap");
            panel.add(divLabel.apply("Is dark: " + Colors.isDark(original.getRGB())), "wrap");

            JPanel temp;

            //reverse
            panel.add(divLabel.apply("Reverse color:"), "wrap");
            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(original);
            temp.add(colorLabel.apply(original));
            panel.add(temp);

            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(reversed);
            temp.add(colorLabel.apply(reversed));
            panel.add(temp, "wrap");

            //analogousColors
            panel.add(divLabel.apply("Analogous color:"), "wrap");
            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(original);
            temp.add(colorLabel.apply(original));
            panel.add(temp);

            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(analogousColors.get(0));
            temp.add(colorLabel.apply(analogousColors.get(0)));
            panel.add(temp);

            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(analogousColors.get(1));
            temp.add(colorLabel.apply(analogousColors.get(1)));
            panel.add(temp, "wrap");

            //triadicColors
            panel.add(divLabel.apply("Triadic color:"), "wrap");
            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(original);
            temp.add(colorLabel.apply(original));

            panel.add(temp);
            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(triadicColors.get(0));
            temp.add(colorLabel.apply(triadicColors.get(0)));

            panel.add(temp);
            temp = new JPanel();
            temp.setPreferredSize(size);
            temp.setBackground(triadicColors.get(1));
            temp.add(colorLabel.apply(triadicColors.get(1)));
            panel.add(temp);

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame();
                frame.add(panel);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
                frame.pack();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            });
        }
    }

    //evaluate darkness
    public static boolean isDark(int hexColor) {
        int r = (hexColor >> 16) & 0xff;
        int g = (hexColor >> 8) & 0xff;
        int b = (hexColor >> 0) & 0xff;
        int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b); //ITU-R BT.709
        return luma < 128;
    }

    //simple reversing
    public static Color reverse(Color elementColor) {
        int maxColorValue = 255;
        return new Color(
                maxColorValue - elementColor.getRed(),
                maxColorValue - elementColor.getGreen(),
                maxColorValue - elementColor.getBlue()
        );
    }

    //two closest color
    public static List<Color> analogousColors(Color elementColor) {
        ArrayList<Color> list = new ArrayList<>(2);
        float[] hsbValues = Color.RGBtoHSB(elementColor.getRed(), elementColor.getGreen(), elementColor.getBlue(), null);
        float hue = hsbValues[0];
        float saturation = hsbValues[1];
        float brightness = hsbValues[2];

        // Calculate two analogous hues
        float analogousHue1 = (hue + 0.083f) % 1.0f;
        float analogousHue2 = (hue - 0.083f + 1.0f) % 1.0f;

        list.add(Color.getHSBColor(analogousHue1, saturation, brightness));
        list.add(Color.getHSBColor(analogousHue2, saturation, brightness));

        return list;
    }

    //two colors from triadic triangle
    public static List<Color> getTriadicColors(Color elementColor) {
        ArrayList<Color> list = new ArrayList<>(2);
        float[] hsbValues = Color.RGBtoHSB(elementColor.getRed(), elementColor.getGreen(), elementColor.getBlue(), null);
        float hue = hsbValues[0];
        float saturation = hsbValues[1];
        float brightness = hsbValues[2];

        // Calculate two triadic hues
        float triadicHue1 = (hue + 0.333f) % 1.0f;
        float triadicHue2 = (hue - 0.333f + 1.0f) % 1.0f;

        list.add(Color.getHSBColor(triadicHue1, saturation, brightness));
        list.add(Color.getHSBColor(triadicHue2, saturation, brightness));

        return list;
    }
}

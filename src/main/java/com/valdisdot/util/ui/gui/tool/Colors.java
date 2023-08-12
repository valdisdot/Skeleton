package com.valdisdot.util.ui.gui.tool;

//util class for colors
public class Colors {
    //for analysing the brightness on a color
    public static int getLuma(int hexColor) {
        int r = (hexColor >> 16) & 0xff;
        int g = (hexColor >> 8) & 0xff;
        int b = (hexColor >> 0) & 0xff;
        int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b); //ITU-R BT.709
        return luma;
    }

    public static boolean isDark(int hexColor) {
        return getLuma(hexColor) < 128;
    }

    public static int reverse(int hexColor) {
        int red = (hexColor >> 16) & 0xFF;
        int green = (hexColor >> 8) & 0xFF;
        int blue = hexColor & 0xFF;
        int maxColorValue = 255;

        int reversedRed = maxColorValue - red;
        int reversedGreen = maxColorValue - green;
        int reversedBlue = maxColorValue - blue;
        return (reversedRed << 16) | (reversedGreen << 8) | reversedBlue;
    }
}

package io.github.ititus.mymod.util;

public class MathUtil {

    public static int scaledClamp(double x, double max, int scaledMax) {
        return scaledClamp(x, max, 0, scaledMax);
    }

    public static int scaledClamp(double x, double max, int scaledMin, int scaledMax) {
        return x > 0 ? max - x > 0 ? 1 + (int) ((scaledMax - scaledMin - 1) * ((x - 1) / (max - 1))) : scaledMax : scaledMin;
    }

}

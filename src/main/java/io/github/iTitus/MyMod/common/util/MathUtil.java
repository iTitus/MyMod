package io.github.iTitus.MyMod.common.util;

public class MathUtil {

	public static final float PI = (float) Math.PI;

	public static double degToRad(double deg) {
		return (deg / 180F) * Math.PI;
	}

	public static float degToRad(float deg) {
		return (deg / 180F) * PI;
	}

}

package io.github.iTitus.MyMod.helper;

public class MathHelper {

	public static final float PI = (float) Math.PI;

	public static float cos(float f) {
		return net.minecraft.util.MathHelper.cos(f);
	}

	public static float degToRad(float deg) {
		return (deg / 180F) * PI;
	}

	public static float sin(float f) {
		return net.minecraft.util.MathHelper.sin(f);
	}

}

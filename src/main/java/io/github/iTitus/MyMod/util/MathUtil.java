package io.github.iTitus.MyMod.util;

import net.minecraft.util.MathHelper;

public class MathUtil {

	public static final float PI = (float) Math.PI;

	public static float cos(float f) {
		return MathHelper.cos(f);
	}

	public static float degToRad(float deg) {
		return (deg / 180F) * PI;
	}

	public static float sin(float f) {
		return MathHelper.sin(f);
	}

}

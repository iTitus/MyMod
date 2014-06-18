package io.github.iTitus.MyMod.util;

public class StringUtil {

	public static String makeNDigits(String s, int n, String c) {

		while (s.length() < n) {
			s = c + s;
		}

		return s;
	}

}

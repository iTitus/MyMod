package com.iTitus.MyMod.helper;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LangHelper {

	public static String localize(String key) {
		return LanguageRegistry.instance().getStringLocalization(key) != "" ? LanguageRegistry
				.instance().getStringLocalization(key) : LanguageRegistry
				.instance().getStringLocalization(key, "en_US");
	}

}

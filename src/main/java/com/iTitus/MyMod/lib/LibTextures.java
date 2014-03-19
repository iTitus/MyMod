package com.iTitus.MyMod.lib;

import java.util.Locale;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.item.EnumItemType;

public class LibTextures {

	public static final String TEXTURE_LOCATION = LibMod.MODID
			.toLowerCase(Locale.ENGLISH);

	public static String getModelTextureLoc(EnumBlockType type) {
		return "textures/models/" + type.name + ".png";
	}

	public static String getTextureLoc(EnumBlockType type) {
		return TEXTURE_LOCATION + ":" + type.name;
	}

	public static String getTextureLoc(EnumItemType type) {
		return TEXTURE_LOCATION + ":" + type.name;
	}

}

package io.github.iTitus.MyMod.lib;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.item.EnumItemType;

import java.util.Locale;

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

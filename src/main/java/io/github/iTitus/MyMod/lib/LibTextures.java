package io.github.iTitus.MyMod.lib;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.item.EnumItemType;
import io.github.iTitus.MyMod.lib.LibModels.Models;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

public class LibTextures {

	public static final String TEXTURE_LOCATION = LibMod.MOD_ID
			.toLowerCase(Locale.ENGLISH),
			MODEL_TEXTURE_LOCATION = "textures/models/";

	public static ResourceLocation getModelResourceLoc(EnumBlockType type) {
		return new ResourceLocation(TEXTURE_LOCATION, getModelTextureLoc(type));
	}

	public static ResourceLocation getModelResourceLoc(EnumItemType type) {
		return new ResourceLocation(TEXTURE_LOCATION, getModelTextureLoc(type));
	}

	public static String getModelTextureLoc(EnumBlockType type) {
		return MODEL_TEXTURE_LOCATION + type.name + ".png";
	}

	public static String getModelTextureLoc(EnumItemType type) {
		return MODEL_TEXTURE_LOCATION + type.name + ".png";
	}

	public static String getModelTextureLoc(Models type) {
		return MODEL_TEXTURE_LOCATION + type.model + ".png";
	}

	public static ResourceLocation getModelTextureResourceLoc(Models type) {
		return new ResourceLocation(TEXTURE_LOCATION, getModelTextureLoc(type));
	}

	public static String getTextureLoc(EnumBlockType type) {
		return TEXTURE_LOCATION + ":" + type.name;
	}

	public static String getTextureLoc(EnumItemType type) {
		return TEXTURE_LOCATION + ":" + type.name;
	}

	public static ResourceLocation getTextureResourceLoc(EnumBlockType type) {
		return new ResourceLocation(TEXTURE_LOCATION, getTextureLoc(type));
	}

	public static ResourceLocation getTextureResourceLoc(EnumItemType type) {
		return new ResourceLocation(TEXTURE_LOCATION, getTextureLoc(type));
	}

}

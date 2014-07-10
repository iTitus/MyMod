package io.github.iTitus.MyMod.common.lib;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.item.EnumItemType;
import io.github.iTitus.MyMod.common.lib.LibModels.Models;
import net.minecraft.util.ResourceLocation;

public class LibTextures {

	public static final String MODEL_TEXTURE_LOCATION = "textures/models/";

	public static ResourceLocation getModelResourceLoc(EnumBlockType type) {
		return new ResourceLocation(MyMod.MOD_ID, getModelTextureLoc(type));
	}

	public static ResourceLocation getModelResourceLoc(EnumItemType type) {
		return new ResourceLocation(MyMod.MOD_ID, getModelTextureLoc(type));
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
		return new ResourceLocation(MyMod.MOD_ID, getModelTextureLoc(type));
	}

	public static String getTextureLoc(EnumBlockType type) {
		return MyMod.MOD_ID + ":" + type.name;
	}

	public static String getTextureLoc(EnumItemType type) {
		return MyMod.MOD_ID + ":" + type.name;
	}

	public static ResourceLocation getTextureResourceLoc(EnumBlockType type) {
		return new ResourceLocation(MyMod.MOD_ID, getTextureLoc(type));
	}

	public static ResourceLocation getTextureResourceLoc(EnumItemType type) {
		return new ResourceLocation(MyMod.MOD_ID, getTextureLoc(type));
	}

}
package com.iTitus.MyMod.item;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item debug;

	public static void init() {

		debug = new ItemDebug();
		GameRegistry.registerItem(debug, EnumItemType.debug.name);

	}

}

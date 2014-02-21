package com.iTitus.MyMod.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {

	public static Item debug;

	public static void init() {

		debug = new ItemDebug();
		GameRegistry.registerItem(debug, EnumItemType.debug.name);

	}

}

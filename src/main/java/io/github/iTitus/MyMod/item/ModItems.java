package io.github.iTitus.MyMod.item;

import io.github.iTitus.MyMod.item.debug.ItemDebug;
import io.github.iTitus.MyMod.item.gun.ItemAmmo;
import io.github.iTitus.MyMod.item.gun.ItemGun;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item debug, gun, ammo;

	public static void init() {

		debug = new ItemDebug();
		GameRegistry.registerItem(debug, debug.getUnlocalizedName());

		gun = new ItemGun();
		GameRegistry.registerItem(gun, gun.getUnlocalizedName());

		ammo = new ItemAmmo();
		GameRegistry.registerItem(ammo, ammo.getUnlocalizedName());

	}

}

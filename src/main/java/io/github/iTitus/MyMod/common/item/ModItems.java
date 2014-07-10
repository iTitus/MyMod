package io.github.iTitus.MyMod.common.item;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.item.debug.ItemDebug;
import io.github.iTitus.MyMod.common.item.gun.ItemAmmo;
import io.github.iTitus.MyMod.common.item.gun.ItemGun;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MyMod.MOD_ID)
public class ModItems {

	public static final ItemAmmo ammo = new ItemAmmo();
	public static final ItemDebug debug = new ItemDebug();
	public static final ItemGun gun = new ItemGun();

	public static void init() {

		GameRegistry.registerItem(debug, debug.getUnlocalizedName());

		GameRegistry.registerItem(gun, gun.getUnlocalizedName());

		GameRegistry.registerItem(ammo, ammo.getUnlocalizedName());

	}

}

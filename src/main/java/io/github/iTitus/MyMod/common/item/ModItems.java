package io.github.iTitus.MyMod.common.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.item.debug.ItemDebug;
import io.github.iTitus.MyMod.common.item.gun.ItemAmmo;
import io.github.iTitus.MyMod.common.item.gun.ItemGun;
import io.github.iTitus.MyMod.common.lib.LibNames;

@ObjectHolder(MyMod.MOD_ID)
public class ModItems {

    public static final MyItem ammo = new ItemAmmo();
    public static final MyItem debug = new ItemDebug();
    public static final MyItem gun = new ItemGun();

    public static void init() {

        GameRegistry.registerItem(debug, LibNames.DEBUG_NAME);

        GameRegistry.registerItem(gun, LibNames.GUN_NAME);

        GameRegistry.registerItem(ammo, LibNames.AMMO_NAME);

    }

}

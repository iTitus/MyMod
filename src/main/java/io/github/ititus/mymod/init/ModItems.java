package io.github.ititus.mymod.init;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.item.ItemBackpack;
import io.github.ititus.mymod.item.ItemDust;
import io.github.ititus.mymod.item.ItemSideConfigurator;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(MyMod.MOD_ID)
public class ModItems {

    @GameRegistry.ObjectHolder(MyMod.DUST)
    public static final ItemDust dust = null;

    @GameRegistry.ObjectHolder(MyMod.SIDE_CONFIGURATOR)
    public static final ItemSideConfigurator sideConfigurator = null;

    @GameRegistry.ObjectHolder(MyMod.BACKPACK)
    public static final ItemBackpack backpack = null;

}

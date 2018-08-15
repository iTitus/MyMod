package io.github.ititus.mymod.init;

import io.github.ititus.mymod.util.creative.tab.ModCreativeTab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModTabs {

    public static final ModCreativeTab MAIN_TAB = new ModCreativeTab("main", () -> new ItemStack(ModBlocks.fallGen != null ? ModBlocks.fallGen : Blocks.FURNACE));
    public static final ModCreativeTab DUST_TAB = new ModCreativeTab("dust", () -> new ItemStack(ModItems.dust != null ? ModItems.dust : Items.GUNPOWDER)).enableSearchBar().setSearchBackground();

}

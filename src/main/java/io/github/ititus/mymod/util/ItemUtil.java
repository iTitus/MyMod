package io.github.ititus.mymod.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

    public static ItemStack getDyeStack(EnumDyeColor color) {
        return getDyeStack(color, 1);
    }

    public static ItemStack getDyeStack(EnumDyeColor color, int count) {
        return new ItemStack(Items.DYE, count, color != null ? color.getDyeDamage() : OreDictionary.WILDCARD_VALUE);
    }

    public static ItemStack getWoolStack(EnumDyeColor color) {
        return getColoredStack(Blocks.WOOL, color, 1);
    }

    public static ItemStack getWoolStack(EnumDyeColor color, int count) {
        return getColoredStack(Blocks.WOOL, color, count);
    }

    public static ItemStack getColoredStack(Block block, EnumDyeColor color, int count) {
        return new ItemStack(block, count, color != null ? color.getMetadata() : OreDictionary.WILDCARD_VALUE);
    }

}

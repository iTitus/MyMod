package com.iTitus.MyMod.recipe;

import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaRecipes {

	public static void init() {

		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.wheel, 1, 1),
				new Object[] { " i ", "isi", "hih", 'i', Items.iron_ingot, 's',
						Items.stick, 'h', Blocks.wooden_slab });
	}

}

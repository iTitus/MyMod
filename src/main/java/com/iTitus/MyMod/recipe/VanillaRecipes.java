package com.iTitus.MyMod.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.iTitus.MyMod.item.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaRecipes {

	public static void init() {

		// GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.wheel, 1, 1),
		// new Object[] { " i ", "isi", "hih", 'i', Items.iron_ingot, 's',
		// Items.stick, 'h', Blocks.wooden_slab });

		GameRegistry.addRecipe(new RecipeAmmo());

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.ammo, 8),
				new Object[] { " i ", "iii", "igi", 'i', Items.iron_ingot, 'g',
						Items.gunpowder });

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.gun), new Object[] {
				"iii", "fi ", "i  ", 'i', Items.iron_ingot, 'f',
				Items.flint_and_steel });

	}

}

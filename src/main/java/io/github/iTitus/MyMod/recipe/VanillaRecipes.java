package io.github.iTitus.MyMod.recipe;

import io.github.iTitus.MyMod.block.ModBlocks;
import io.github.iTitus.MyMod.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaRecipes {

	public static void init() {

		// GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.wheel, 1, 1),
		// new Object[] { " i ", "isi", "hih", 'i', Items.iron_ingot, 's',
		// Items.stick, 'h', Blocks.wooden_slab });

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.display, 1, 0),
				new ItemStack(ModBlocks.display, 1, 2));

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.display, 1, 1),
				new ItemStack(ModBlocks.display, 1, 0));

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.display, 1, 2),
				new ItemStack(ModBlocks.display, 1, 1));

		GameRegistry.addRecipe(new RecipeAmmo());

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.ammo, 8),
				new Object[] { " i ", "iii", "igi", 'i', Items.iron_ingot, 'g',
						Items.gunpowder });

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.gun), new Object[] {
				"iii", "fi ", "i  ", 'i', Items.iron_ingot, 'f',
				Items.flint_and_steel });

	}

}

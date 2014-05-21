package com.iTitus.MyMod.recipe;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.iTitus.MyMod.helper.InventoryHelper;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.item.gun.EnumModifierType;

public class RecipeAmmo implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting crafting, World world) {

		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ArrayList<Item> items = new ArrayList<Item>();

		for (int i = 0; i < crafting.getSizeInventory(); i++) {

			ItemStack stack = crafting.getStackInSlot(i);

			if (stack != null) {
				stacks.add(stack);
				items.add(stack.getItem());
			}

		}

		ArrayList al = new ArrayList();
		al.addAll(stacks);
		HashSet hs = new HashSet();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);

		ArrayList al2 = new ArrayList();
		al2.addAll(items);
		HashSet hs2 = new HashSet();
		hs2.addAll(al2);
		al2.clear();
		al2.addAll(hs2);

		System.out.println(stacks + " - " + al);
		System.out.println(items + " - " + al2);

		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		return new ItemStack(ModItems.ammo);
	}

	@Override
	public int getRecipeSize() {
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.ammo);
	}

}

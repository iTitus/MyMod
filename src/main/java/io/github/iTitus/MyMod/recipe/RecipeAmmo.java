package io.github.iTitus.MyMod.recipe;

import io.github.iTitus.MyMod.item.ModItems;
import io.github.iTitus.MyMod.item.gun.EnumModifierType;
import io.github.iTitus.MyMod.item.gun.ItemAmmo;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeAmmo implements IRecipe {

	private ItemStack output;

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		return output.copy();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public boolean matches(InventoryCrafting crafting, World world) {

		output = null;
		HashMap<EnumModifierType, Integer> modifiers = new HashMap<EnumModifierType, Integer>();
		ItemStack itemstack = null;
		ArrayList arraylist = new ArrayList();

		for (int i = 0; i < crafting.getSizeInventory(); ++i) {

			ItemStack itemstack1 = crafting.getStackInSlot(i);

			if (itemstack1 != null) {

				if (itemstack1.getItem() instanceof ItemAmmo) {

					if (itemstack != null) {
						return false;
					}

					ItemAmmo.merge(modifiers,
							ItemAmmo.readFromNBT(itemstack1.getTagCompound()));

					itemstack = itemstack1;

				} else {

					if (!EnumModifierType.isModifier(itemstack1))
						return false;

					HashMap<EnumModifierType, Integer> modifierOnStack = new HashMap<EnumModifierType, Integer>();
					modifierOnStack.put(
							EnumModifierType.getForStack(itemstack1), 1);
					ItemAmmo.merge(modifiers, modifierOnStack);

					arraylist.add(itemstack1);
				}
			}
		}

		output = new ItemStack(ModItems.ammo);
		output.setTagCompound(ItemAmmo.writeToNBT(modifiers,
				new NBTTagCompound()));

		return itemstack != null && !arraylist.isEmpty()
				&& ItemAmmo.isValidAmmo(modifiers);
	}

}

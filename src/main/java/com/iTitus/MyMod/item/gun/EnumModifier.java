package com.iTitus.MyMod.item.gun;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum EnumModifier {

	gunpowder(new ItemStack(Items.gunpowder), 0) {

		@Override
		public void onShoot() {
			// add velocity
		}

	};

	private ItemStack stack;
	private int maxStackSize;

	private EnumModifier(ItemStack stack, int maxStackSize) {
		this.stack = stack;
		this.maxStackSize = maxStackSize;
	}

	public void onUpdate() {

	}

	public void onImpact() {

	}

	public void onShoot() {

	}

}

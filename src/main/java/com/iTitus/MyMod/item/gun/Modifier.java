package com.iTitus.MyMod.item.gun;

import net.minecraft.item.ItemStack;

public class Modifier {

	private EnumModifierType modifier;
	private int count;

	public Modifier(EnumModifierType modifier, int count) {
		this.modifier = modifier;
		this.count = count;
	}

	public EnumModifierType getModifierType() {
		return modifier;
	}

	public int getCount() {
		return count;
	}

}

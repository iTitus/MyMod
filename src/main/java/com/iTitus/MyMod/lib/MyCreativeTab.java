package com.iTitus.MyMod.lib;

import com.iTitus.MyMod.block.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class MyCreativeTab extends CreativeTabs {

	public static final CreativeTabs INSTANCE = new MyCreativeTab();

	private MyCreativeTab() {
		super(LibMod.MODID);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(ModBlocks.zero);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}

package com.iTitus.MyMod.lib;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.iTitus.MyMod.block.ModBlocks;

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

	@Override
	public void displayAllReleventItems(List list) {
		super.displayAllReleventItems(list);

	}

}

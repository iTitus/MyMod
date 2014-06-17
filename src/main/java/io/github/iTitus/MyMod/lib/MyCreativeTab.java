package io.github.iTitus.MyMod.lib;

import io.github.iTitus.MyMod.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MyCreativeTab extends CreativeTabs {

	public static final CreativeTabs INSTANCE = new MyCreativeTab();

	private MyCreativeTab() {
		super(LibMod.MOD_ID);
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

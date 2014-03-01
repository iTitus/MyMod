package com.iTitus.MyMod.item;

import java.util.List;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemWheel extends MyItemBlock {

	public ItemWheel() {
		super(ModBlocks.wheel, EnumBlockType.WHEEL);
		setHasSubtypes(true);
	}

	public ItemWheel(Block b) {
		this();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {

		list.add(stack.getItemDamage() == 0 ? "Filled" : "Empty");
		super.addInformation(stack, player, list, par4);
	}

}

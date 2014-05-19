package com.iTitus.MyMod.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.block.ModBlocks;

public class ItemWheel extends MyItemBlock {

	public ItemWheel(Block b) {
		super(ModBlocks.wheel, EnumBlockType.WHEEL);
		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {

		list.add(stack.getItemDamage() == 0 ? StatCollector
				.translateToLocal("lore.wheel.0") : StatCollector
				.translateToLocal("lore.wheel.1"));
		super.addInformation(stack, player, list, par4);
	}
}

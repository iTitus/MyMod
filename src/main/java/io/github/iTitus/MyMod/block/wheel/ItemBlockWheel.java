package io.github.iTitus.MyMod.block.wheel;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.block.ModBlocks;
import io.github.iTitus.MyMod.item.MyItemBlock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockWheel extends MyItemBlock {

	public ItemBlockWheel(Block b) {
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

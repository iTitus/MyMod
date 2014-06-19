package io.github.iTitus.MyMod.block.display;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.item.MyItemBlock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockDisplay extends MyItemBlock {

	public ItemBlockDisplay(Block b) {
		super(b, EnumBlockType.DISPLAY);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean b) {
		super.addInformation(stack, player, list, b);
	}

}

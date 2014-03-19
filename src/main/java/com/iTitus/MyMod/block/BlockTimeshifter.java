package com.iTitus.MyMod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.iTitus.MyMod.helper.LangHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTimeshifter extends MyBlock {

	@SideOnly(Side.CLIENT)
	private static IIcon icon;

	public BlockTimeshifter() {
		super(EnumBlockType.TIMESHIFTER);
		setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (!world.isRemote && player.isSneaking()) {
			if (world.getBlockMetadata(x, y, z) != 1) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
				player.addChatMessage(new ChatComponentText(LangHelper
						.localize("message.timeshifter.nightmode")));
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				player.addChatMessage(new ChatComponentText(LangHelper
						.localize("message.timeshifter.daymode")));
			}
		}

		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY,
				hitZ);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z,
			Block neighbour) {

		if (!world.isRemote) {
			switch (world.getBlockMetadata(x, y, z)) {
			case 0: {
				world.setWorldTime(6000);
				break;
			}
			case 1: {
				world.setWorldTime(18000);
				break;
			}
			default:
				break;
			}

		}

		super.onNeighborBlockChange(world, x, y, z, neighbour);
	}

}

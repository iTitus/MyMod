package io.github.iTitus.MyMod.block.timeshifter;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.block.MyBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTimeshifter extends MyBlock {

	@SideOnly(Side.CLIENT)
	private static IIcon icon;

	public BlockTimeshifter() {
		super(EnumBlockType.TIMESHIFTER);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (!world.isRemote && player.isSneaking()) {
			if (world.getBlockMetadata(x, y, z) != 1) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
				player.addChatMessage(new ChatComponentText(StatCollector
						.translateToLocal("message.timeshifter.nightmode")));
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				player.addChatMessage(new ChatComponentText(StatCollector
						.translateToLocal("message.timeshifter.daymode")));
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

package com.iTitus.MyMod.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.iTitus.MyMod.helper.LangHelper;
import com.iTitus.MyMod.lib.LibNames;
import com.iTitus.MyMod.lib.LibTextures;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;

public class BlockTimeshifter extends MyBlock {

	@SideOnly(Side.CLIENT)
	private static IIcon icon;

	public BlockTimeshifter() {
		super(EnumBlockType.TIMESHIFTER);
		setTickRandomly(true);
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

}

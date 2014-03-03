package com.iTitus.MyMod.block;

import java.util.List;

import com.iTitus.MyMod.helper.InventoryHelper;
import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockWheel extends MyBlock implements ITileEntityProvider {

	public BlockWheel() {
		super(EnumBlockType.WHEEL);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer p, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return false;

		if (world.getTileEntity(x, y, z) instanceof TileEntityWheel) {
			return ((TileEntityWheel) world.getTileEntity(x, y, z))
					.onBlockActivated(p, side, hitX, hitY, hitZ);
		}

		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return LibRender.WHEEL_ID;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y,
			int z, int side) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWheel();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean dropAllItems() {
		return false;
	}

	@Override
	public int damageDropped(int dmg) {
		switch (dmg) {
		case 0:
			return 0;
		default:
			return 1;
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < 2; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}

}

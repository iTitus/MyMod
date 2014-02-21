package com.iTitus.MyMod.block;

import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockWheel extends MyBlock implements ITileEntityProvider {

	public BlockWheel() {
		super(EnumBlockType.WHEEL);
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
	public void registerBlockIcons(IIconRegister register) {

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWheel();
	}

	@Override
	public boolean putInTab() {
		return true;
	}

}

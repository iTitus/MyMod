package com.iTitus.MyMod.block;

import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockWheel extends MyBlock implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	private static IIcon icon;

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
		icon = register.registerIcon(LibTextures
				.getTextureLoc(EnumBlockType.WHEEL));
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon;
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

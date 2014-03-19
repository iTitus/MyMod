package com.iTitus.MyMod.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWheel extends MyBlock implements ITileEntityProvider {

	public BlockWheel() {
		super(EnumBlockType.WHEEL);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWheel();
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

	@Override
	public boolean dropAllItems() {
		return false;
	}

	@Override
	public int getRenderType() {
		return LibRender.WHEEL_ID;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for (int i = 0; i < 2; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer p, int side, float hitX, float hitY, float hitZ) {

		if (!world.isRemote
				&& world.getTileEntity(x, y, z) instanceof TileEntityWheel) {
			return ((TileEntityWheel) world.getTileEntity(x, y, z))
					.onBlockActivated(p, side, hitX, hitY, hitZ);
		}

		return false;
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {

		if (!world.isRemote
				&& world.getTileEntity(x, y, z) instanceof TileEntityWheel) {
			((TileEntityWheel) world.getTileEntity(x, y, z))
					.setMode((world.getBlockMetadata(x, y, z) == 0) ? (TileEntityWheel.Mode.FILLED)
							: (TileEntityWheel.Mode.EMPTY));
		}

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

}

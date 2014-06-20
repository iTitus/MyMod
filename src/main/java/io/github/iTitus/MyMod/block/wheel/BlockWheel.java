package io.github.iTitus.MyMod.block.wheel;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.block.MyBlockContainer;
import io.github.iTitus.MyMod.lib.LibRender;
import io.github.iTitus.MyMod.tileentity.wheel.TileEntityWheel;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWheel extends MyBlockContainer {

	public BlockWheel() {
		super(EnumBlockType.WHEEL);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWheel();
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

		// if (!world.isRemote
		// && world.getTileEntity(x, y, z) instanceof TileEntityWheel) {
		// return ((TileEntityWheel) world.getTileEntity(x, y, z))
		// .onBlockActivated(p, side, hitX, hitY, hitZ);
		// }

		return false;
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {

		// if (!world.isRemote
		// && world.getTileEntity(x, y, z) instanceof TileEntityWheel) {
		// ((TileEntityWheel) world.getTileEntity(x, y, z))
		// .setMode((world.getBlockMetadata(x, y, z) == 0) ?
		// (TileEntityWheel.Mode.FILLED)
		// : (TileEntityWheel.Mode.EMPTY));
		// }

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

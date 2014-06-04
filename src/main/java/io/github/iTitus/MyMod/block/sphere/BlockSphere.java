package io.github.iTitus.MyMod.block.sphere;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.block.MyBlock;
import io.github.iTitus.MyMod.tileentity.sphere.TileEntitySphere;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSphere extends MyBlock implements ITileEntityProvider {

	public BlockSphere() {
		super(EnumBlockType.SPHERE);
		setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
		isBlockContainer = true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySphere();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z,
			int eventNumber, int argument) {

		super.onBlockEventReceived(world, x, y, z, eventNumber, argument);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(eventNumber,
				argument) : false;

	}

}

package io.github.iTitus.MyMod.common.block.zero;

import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.block.MyBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZero extends MyBlock {

	public BlockZero() {
		super(EnumBlockType.ZERO);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x,
			int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y,
			int z, int side) {

		Block block = world.getBlock(x, y, z);

		if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x
				- Facing.offsetsXForSide[side], y
				- Facing.offsetsYForSide[side], z
				- Facing.offsetsZForSide[side])) {
			return true;
		}

		if (block == this) {
			return false;
		}

		return block == this ? false : super.shouldSideBeRendered(world, x, y,
				z, side);
	}

}

package com.iTitus.MyMod.block;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockZero extends MyBlock {

	protected BlockZero() {
		super(EnumBlockType.ZERO);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}

package io.github.iTitus.MyMod.common.block.sphere;

import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.block.MyBlockContainer;
import io.github.iTitus.MyMod.common.tileentity.sphere.TileEntitySphere;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSphere extends MyBlockContainer {

    public BlockSphere() {
        super(EnumBlockType.SPHERE);
        setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySphere();
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

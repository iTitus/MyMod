package io.github.ititus.mymod.block;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.tile.TileFallGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFallGen extends BlockContainerBase {

    public BlockFallGen() {
        super(MyMod.FALL_GEN);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFallGen();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(MyMod.instance, GuiHandler.FALL_GEN, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (entity != null && !world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileFallGen) {
                ((TileFallGen) tile).onFallenUpon(entity, fallDistance);
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }
}

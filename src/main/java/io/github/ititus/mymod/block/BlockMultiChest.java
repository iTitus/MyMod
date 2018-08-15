package io.github.ititus.mymod.block;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.tile.TileMultiChest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMultiChest extends BlockContainerBase {

    public BlockMultiChest() {
        super(MyMod.MULTI_CHEST, Material.WOOD);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMultiChest();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() == Items.STICK) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileMultiChest) {
                    ((TileMultiChest) tile).showGridParticles();
                }
            } else {
                player.openGui(MyMod.instance, GuiHandler.MULTI_CHEST, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (block == ModBlocks.multiChest) {
            int chests = 1;
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos pos_ = fromPos.offset(facing);
                if (!pos_.equals(pos)) {
                    IBlockState state_ = world.getBlockState(pos_);
                    if (state_.getBlock() == ModBlocks.multiChest) {
                        chests++;
                        break;
                    }
                }
            }
            if (chests > 1) {
                world.addBlockEvent(pos, state.getBlock(), 0, 0);
            }
        }
    }
}

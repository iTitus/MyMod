package io.github.ititus.mymod.item;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSideConfigurator extends ItemBase {

    public ItemSideConfigurator() {
        super(MyMod.SIDE_CONFIGURATOR);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemSideConfigurator) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock().hasTileEntity(state)) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileBase && ((TileBase) tile).hasSideConfiguration()) {
                    player.openGui(MyMod.instance, GuiHandler.SIDE_CONFIGURATOR, world, pos.getX(), pos.getY(), pos.getZ());
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }
}

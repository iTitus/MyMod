package io.github.ititus.mymod.block;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.item.block.ItemBlockTank;
import io.github.ititus.mymod.tile.TileTank;
import io.github.ititus.mymod.util.fluid.FluidTankBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockTank extends BlockContainerBase {

    public BlockTank() {
        super(MyMod.TANK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileTank();
    }

    @Override
    public Item getItemBlock() {
        return new ItemBlockTank(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileTank) {
                if (tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing) && FluidUtil.interactWithFluidHandler(player, hand, tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))) {
                    return true;
                }
                player.openGui(MyMod.instance, GuiHandler.TANK, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!world.isRemote) {
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (itemHandler instanceof FluidTankBase) {
                    FluidTankBase itemTank = (FluidTankBase) itemHandler;
                    TileEntity tile = world.getTileEntity(pos);
                    if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                        IFluidHandler tileHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (tileHandler instanceof FluidTankBase) {
                            FluidTankBase tileTank = (FluidTankBase) tileHandler;
                            FluidStack fluid = itemTank.getFluid();
                            if (fluid != null) {
                                tileTank.setFluid(fluid.copy());
                                //world.notifyBlockUpdate(pos, state, state, 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean dropInventory() {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        if (!drops.isEmpty()) {
            ItemStack stack = drops.get(0);
            if (!stack.isEmpty()) {
                fillStack(stack, world, pos, state);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getFilledStack(world, pos, state);
    }

    private ItemStack getFilledStack(IBlockAccess world, BlockPos pos, IBlockState state) {
        ItemStack stack = new ItemStack(this);
        fillStack(stack, world, pos, state);
        return stack;
    }

    private void fillStack(ItemStack stack, IBlockAccess world, BlockPos pos, IBlockState state) {
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (itemHandler instanceof FluidTankBase) {
                FluidTankBase itemTank = (FluidTankBase) itemHandler;
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                    IFluidHandler tileHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    if (tileHandler instanceof FluidTankBase) {
                        FluidTankBase tileTank = (FluidTankBase) tileHandler;
                        itemTank.setFluid(tileTank.getFluid());
                    }
                }
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }
}

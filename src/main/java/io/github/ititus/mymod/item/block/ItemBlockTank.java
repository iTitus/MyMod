package io.github.ititus.mymod.item.block;

import io.github.ititus.mymod.block.BlockTank;
import io.github.ititus.mymod.tile.TileTank;
import io.github.ititus.mymod.util.fluid.FluidTankBase;
import io.github.ititus.mymod.util.fluid.FluidTankItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockTank extends ItemBlockBase {

    public ItemBlockTank(BlockTank block) {
        super(block);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public BlockTank getBlock() {
        return (BlockTank) super.getBlock();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem tank = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (tank != null) {
                IFluidTankProperties prop = tank.getTankProperties()[0];
                FluidStack fluid = prop.getContents();
                tooltip.add(fluid != null ? fluid.getLocalizedName() : I18n.translateToLocal("text.mymod:fluid.empty"));
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("text.mymod:fluid.stored", fluid != null ? fluid.amount : 0, prop.getCapacity()));
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            items.add(getFilledStack(null));
            FluidRegistry.getRegisteredFluids().forEach((name, fluid) -> {
                if (fluid != null) {
                    items.add(getFilledStack(new FluidStack(fluid, TileTank.CAPACITY)));
                }
            });
            items.add(getFilledStack(new FluidStack(FluidRegistry.WATER, TileTank.CAPACITY)));
        }
    }

    private ItemStack getFilledStack(FluidStack fluidStack) {
        ItemStack stack = new ItemStack(this);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem tank = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (tank instanceof FluidTankBase) {
                ((FluidTankBase) tank).setFluid(fluidStack);
            }
        }
        return stack;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidTankItem.CapabilityProvider(stack, TileTank.CAPACITY);
    }

}

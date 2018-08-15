package io.github.ititus.mymod.util.fluid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidTankItem extends FluidTankBase implements IFluidHandlerItem {

    protected final ItemStack stack;

    public FluidTankItem(ItemStack stack, int capacity) {
        super(capacity);
        this.stack = stack;
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
        return stack;
    }

    public void readFromNBT() {
        readFromNBT(stack.getSubCompound("tank"));
    }

    public void writeToNBT() {
        writeToNBT(stack.getOrCreateSubCompound("tank"));
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt) {
        fluid = nbt == null || nbt.hasKey("Empty") ? null : FluidStack.loadFluidStackFromNBT(nbt);
        return this;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (fluid != null) {
            fluid.writeToNBT(nbt);
            nbt.removeTag("Empty");
        } else {
            nbt.setString("Empty", "");
        }
        return nbt;
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        readFromNBT();
        return super.getFluid();
    }

    @Override
    public void setFluid(@Nullable FluidStack fluid) {
        super.setFluid(fluid);
        writeToNBT();
    }

    @Override
    public int getFluidAmount() {
        readFromNBT();
        return super.getFluidAmount();
    }

    @Override
    public FluidTankInfo getInfo() {
        readFromNBT();
        return super.getInfo();
    }

    @Override
    public int fillInternal(FluidStack resource, boolean doFill) {
        readFromNBT();
        int filled = super.fillInternal(resource, doFill);
        writeToNBT();
        return filled;
    }

    @Nullable
    @Override
    public FluidStack drainInternal(FluidStack resource, boolean doDrain) {
        readFromNBT();
        FluidStack drained = super.drainInternal(resource, doDrain);
        writeToNBT();
        return drained;
    }

    @Nullable
    @Override
    public FluidStack drainInternal(int maxDrain, boolean doDrain) {
        readFromNBT();
        FluidStack drained = super.drainInternal(maxDrain, doDrain);
        writeToNBT();
        return drained;
    }

    public static class CapabilityProvider extends FluidTankItem implements ICapabilityProvider {

        public CapabilityProvider(ItemStack stack, int capacity) {
            super(stack, capacity);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? (T) this : null;
        }
    }
}

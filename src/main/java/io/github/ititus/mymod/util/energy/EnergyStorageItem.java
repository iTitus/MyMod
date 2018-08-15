package io.github.ititus.mymod.util.energy;

import io.github.ititus.mymod.MyMod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyStorageItem extends EnergyStorageBase {

    public static final ResourceLocation ENERGY_PROPERTY_OVERRIDE = new ResourceLocation(MyMod.MOD_ID, "energy");

    protected final ItemStack stack;

    public EnergyStorageItem(ItemStack stack, int capacity) {
        this(stack, capacity, capacity, capacity, 0);
    }

    public EnergyStorageItem(ItemStack stack, int capacity, int maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorageItem(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this(stack, capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorageItem(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
        this.stack = stack;
    }

    @Override
    public int getEnergyStored() {
        if (stack.hasTagCompound()) {
            return stack.getTagCompound().getInteger("energy");
        }
        return 0;
    }

    @Override
    public void setEnergyStored(int energy) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("energy", Math.max(0, Math.min(getMaxEnergyStored(), energy)));
    }

    public static class CapabilityProvider extends EnergyStorageItem implements ICapabilityProvider {

        public CapabilityProvider(ItemStack stack, int capacity) {
            super(stack, capacity);
        }

        public CapabilityProvider(ItemStack stack, int capacity, int maxTransfer) {
            super(stack, capacity, maxTransfer);
        }

        public CapabilityProvider(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
            super(stack, capacity, maxReceive, maxExtract);
        }

        public CapabilityProvider(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
            super(stack, capacity, maxReceive, maxExtract, energy);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY ? (T) this : null;
        }
    }
}

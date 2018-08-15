package io.github.ititus.mymod.item.block;

import io.github.ititus.mymod.block.BlockEnergyCell;
import io.github.ititus.mymod.tile.TileEnergyCell;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import io.github.ititus.mymod.util.energy.EnergyStorageItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockEnergyCell extends ItemBlockBase {

    public ItemBlockEnergyCell(BlockEnergyCell block) {
        super(block);
        setHasSubtypes(true);
        setMaxStackSize(1);
        addPropertyOverride(EnergyStorageItem.ENERGY_PROPERTY_OVERRIDE, (stack, world, entity) -> {
            if (stack.getMetadata() == 0 && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
                if (storage != null) {
                    return MathUtil.scaledClamp(storage.getEnergyStored(), storage.getMaxEnergyStored(), 10) / 10F;
                }
            }
            return -1;
        });
    }

    @Override
    public BlockEnergyCell getBlock() {
        return (BlockEnergyCell) super.getBlock();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return stack.getMetadata() == 0 ? super.getUnlocalizedName(stack) : "tile.mymod:creative_energy_cell";
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (stack.getMetadata() == 0 && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energy != null) {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("text.mymod:energy.stored", energy.getEnergyStored(), energy.getMaxEnergyStored()));
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
                if (energy instanceof EnergyStorageBase) {
                    ((EnergyStorageBase) energy).setEnergyStored(0);
                }
            }
            items.add(stack);

            stack = new ItemStack(this);
            if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
                if (energy instanceof EnergyStorageBase) {
                    ((EnergyStorageBase) energy).setEnergyStored(energy.getMaxEnergyStored());
                }
            }
            items.add(stack);

            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.getMetadata() == 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.getMetadata() == 0 && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energy != null) {
                return (13D - MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), 13)) / 13D;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return stack.getMetadata() == 0 ? new EnergyStorageItem.CapabilityProvider(stack, TileEnergyCell.CAPACITY, TileEnergyCell.MAX_TRANSFER) : CreativeEnergyCapabilityProvider.INSTANCE;
    }

    private static class CreativeEnergyCapabilityProvider extends EnergyStorageBase implements ICapabilityProvider {

        private static final int CAPACITY = Integer.MAX_VALUE;

        private static final CreativeEnergyCapabilityProvider INSTANCE = new CreativeEnergyCapabilityProvider();

        public CreativeEnergyCapabilityProvider() {
            super(CAPACITY);
            this.energy = CAPACITY / 2;
        }

        @Override
        public void setEnergyStored(int energy) {
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return maxReceive;
        }

        @Override
        public int receiveEnergyInternal(int maxReceive, boolean simulate) {
            return maxReceive;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return maxExtract;
        }

        @Override
        public int extractEnergyInternal(int maxExtract, boolean simulate) {
            return maxExtract;
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

package io.github.ititus.mymod.tile;

import com.google.common.collect.Sets;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyProducer;
import io.github.ititus.mymod.util.network.NetworkState;
import io.github.ititus.mymod.util.weight.WeightHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.Set;

public class TileFallGen extends TileBase implements ITickable {

    public static final int CAPACITY = 10_000;
    private static final double FE_PER_WEIGHT = 10;

    public final EnergyProducer energy = new EnergyProducer(CAPACITY);
    private int energyLastTick;

    public TileFallGen() {
        super(ModBlocks.fallGen.getRegistryName().toString());
    }

    @Override
    public void update() {
        if (energy.getEnergyStored() != energyLastTick) {
            energyLastTick = energy.getEnergyStored();
            markDirty();
        }

        if (world.isRemote) {
            return;
        }
        sendEnergy();
    }

    private void sendEnergy() {
        if (energy.getEnergyStored() > 0) {
            Set<IEnergyStorage> receivers = Sets.newHashSet();
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity tile = world.getTileEntity(pos.offset(facing));
                if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                    IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (storage != null && storage.canReceive() && storage.receiveEnergy(1, true) > 0) {
                        receivers.add(storage);
                    }
                }
            }

            if (!receivers.isEmpty()) {
                int maxTransfer = Math.max(1, Math.min(energy.getMaxExtract(), energy.getEnergyStored()) / receivers.size());
                for (IEnergyStorage storage : receivers) {
                    energy.extractEnergy(storage.receiveEnergy(energy.extractEnergy(maxTransfer, true), false), false);
                    if (energy.getEnergyStored() <= 0) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("energy")) {
            CapabilityEnergy.ENERGY.readNBT(energy, null, compound.getTag("energy"));
        }
        energyLastTick = energy.getEnergyStored();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(energy, null));
        return compound;
    }

    @Override
    public EnergyProducer getEnergy() {
        return energy;
    }

    public void onFallenUpon(@Nonnull Entity entity, float fallDistance) {
        double energyProduced = WeightHelper.getWeight(entity) * FE_PER_WEIGHT * fallDistance;
        System.out.println("entity = [" + entity + "], fallDistance = [" + fallDistance + "], energyProduced = [" + energyProduced + "]");
        System.out.println("energyProduced = " + energyProduced);
        energy.receiveEnergyInternal((int) energyProduced, false);
    }

    @Override
    public int getComparatorLevel() {
        return MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), 15);
    }

    @Override
    public NetworkState getNetworkState() {
        return new NetworkState(1, 0, 1);
    }

    @Override
    public void updateNetworkState(NetworkState state) {
        super.updateNetworkState(state);
        state.set(0, energy.getEnergyStored());
    }

    @Override
    public void loadFromNetworkState(NetworkState state) {
        super.loadFromNetworkState(state);
        energy.setEnergyStored(state.getInt(0));
    }

}

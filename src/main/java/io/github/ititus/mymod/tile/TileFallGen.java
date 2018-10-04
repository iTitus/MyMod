package io.github.ititus.mymod.tile;

import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyProducer;
import io.github.ititus.mymod.util.network.NetworkState;
import io.github.ititus.mymod.util.weight.MassHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TileFallGen extends TileBase implements ITickable {

    public static final int CAPACITY = 64_000;
    /**
     * 1/2 * Gravity * FE/Energy
     */
    private static final double FE_FACTOR = 0.5 * 10 * 10;

    public final EnergyProducer energy = new EnergyProducer(CAPACITY);
    private int energyLastTick, offset;

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
            List<IEnergyStorage> receivers = new ArrayList<>(EnumFacing.VALUES.length);
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
                int maxExtract = energy.getMaxExtract();
                int toSend = receivers.size();

                for (int i = 0; i < receivers.size(); i++) {
                    IEnergyStorage storage = receivers.get((offset + i) % EnumFacing.VALUES.length);
                    int maxTransfer = MathHelper.ceil(Math.min(maxExtract, energy.getEnergyStored()) / (double) toSend);
                    maxExtract -= energy.extractEnergy(storage.receiveEnergy(energy.extractEnergy(maxTransfer, true), false), false);
                    toSend--;
                    if (energy.getEnergyStored() <= 0) {
                        return;
                    }
                }

                offset++;
                offset %= EnumFacing.VALUES.length;
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
        if (!(entity instanceof FakePlayer)) {
            double mass = MassHelper.getEntityMass(entity);
            double energyProducedD = FE_FACTOR * mass * fallDistance;
            int energyProduced = (int) energyProducedD;
            System.out.println("entity = [" + entity + "], mass = [" + mass + "], fallDistance = [" + fallDistance + "], energyProduced = [" + energyProduced + "]");
            energy.receiveEnergyInternal(energyProduced, false);
        }
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

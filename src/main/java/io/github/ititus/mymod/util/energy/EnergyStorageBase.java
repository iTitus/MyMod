package io.github.ititus.mymod.util.energy;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageBase extends EnergyStorage {

    public EnergyStorageBase(int capacity) {
        super(capacity);
    }

    public EnergyStorageBase(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorageBase(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorageBase(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energy = getEnergyStored();
        int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(getMaxReceive(), maxReceive));
        if (!simulate)
            setEnergyStored(energy + energyReceived);
        return energyReceived;
    }

    public int receiveEnergyInternal(int maxReceive, boolean simulate) {
        int energy = getEnergyStored();
        int energyReceived = Math.min(getMaxEnergyStored() - energy, maxReceive);
        if (!simulate)
            setEnergyStored(energy + energyReceived);
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energy = getEnergyStored();
        int energyExtracted = Math.min(energy, Math.min(getMaxExtract(), maxExtract));
        if (!simulate)
            setEnergyStored(energy - energyExtracted);
        return energyExtracted;
    }

    public int extractEnergyInternal(int maxExtract, boolean simulate) {
        int energy = getEnergyStored();
        int energyExtracted = Math.min(energy, maxExtract);
        if (!simulate)
            setEnergyStored(energy - energyExtracted);
        return energyExtracted;
    }

    public int getFreeSpace() {
        return getMaxEnergyStored() - getEnergyStored();
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    @Override
    public boolean canExtract() {
        return getMaxExtract() > 0;
    }

    @Override
    public boolean canReceive() {
        return getMaxReceive() > 0;
    }

    public void setEnergyStored(int energy) {
        this.energy = Math.max(0, Math.min(getMaxEnergyStored(), energy));
    }

}

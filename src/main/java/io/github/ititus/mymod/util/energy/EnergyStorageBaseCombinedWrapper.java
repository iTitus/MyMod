package io.github.ititus.mymod.util.energy;

import java.util.Arrays;

public class EnergyStorageBaseCombinedWrapper extends EnergyStorageBase {

    protected final EnergyStorageBase[] energyStorages;

    public EnergyStorageBaseCombinedWrapper(EnergyStorageBase... energyStorages) {
        super(0);
        this.energyStorages = energyStorages;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = 0;
        for (EnergyStorageBase energyStorage : energyStorages) {
            received += energyStorage.receiveEnergy(maxReceive - received, simulate);
            if (received >= maxReceive) {
                break;
            }
        }
        return received;
    }

    @Override
    public int receiveEnergyInternal(int maxReceive, boolean simulate) {
        int received = 0;
        for (EnergyStorageBase energyStorage : energyStorages) {
            received += energyStorage.receiveEnergyInternal(maxReceive - received, simulate);
            if (received >= maxReceive) {
                break;
            }
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = 0;
        for (EnergyStorageBase energyStorage : energyStorages) {
            extracted += energyStorage.extractEnergy(maxExtract - extracted, simulate);
            if (extracted >= maxReceive) {
                break;
            }
        }
        return extracted;
    }

    @Override
    public int extractEnergyInternal(int maxExtract, boolean simulate) {
        int extracted = 0;
        for (EnergyStorageBase energyStorage : energyStorages) {
            extracted += energyStorage.extractEnergyInternal(maxExtract - extracted, simulate);
            if (extracted >= maxReceive) {
                break;
            }
        }
        return extracted;
    }

    @Override
    public int getFreeSpace() {
        return Arrays.stream(energyStorages).mapToInt(EnergyStorageBase::getFreeSpace).sum();
    }

    @Override
    public int getMaxReceive() {
        return Arrays.stream(energyStorages).mapToInt(EnergyStorageBase::getMaxReceive).sum();
    }

    @Override
    public int getMaxExtract() {
        return Arrays.stream(energyStorages).mapToInt(EnergyStorageBase::getMaxExtract).sum();
    }

    @Override
    public boolean canExtract() {
        return Arrays.stream(energyStorages).anyMatch(EnergyStorageBase::canExtract);
    }

    @Override
    public boolean canReceive() {
        return Arrays.stream(energyStorages).anyMatch(EnergyStorageBase::canReceive);
    }

    @Override
    public void setEnergyStored(int energy) {
        int set = 0;
        for (EnergyStorageBase energyStorage : energyStorages) {
            int toSet = Math.max(0, Math.min(energy - set, energyStorage.getMaxEnergyStored()));
            energyStorage.setEnergyStored(toSet);
            set += toSet;
        }
    }

    @Override
    public int getEnergyStored() {
        return Arrays.stream(energyStorages).mapToInt(EnergyStorageBase::getEnergyStored).sum();
    }

    @Override
    public int getMaxEnergyStored() {
        return Arrays.stream(energyStorages).mapToInt(EnergyStorageBase::getMaxEnergyStored).sum();
    }
}

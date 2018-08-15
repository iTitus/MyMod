package io.github.ititus.mymod.util.energy;

public class EnergyAcceptor extends EnergyStorageBase {

    public EnergyAcceptor(int capacity) {
        this(capacity, capacity);
    }

    public EnergyAcceptor(int capacity, int maxReceive) {
        this(capacity, maxReceive, 0);
    }

    public EnergyAcceptor(int capacity, int maxReceive, int energy) {
        super(capacity, maxReceive, 0, energy);
    }


}

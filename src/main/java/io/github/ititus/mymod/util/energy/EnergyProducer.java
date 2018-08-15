package io.github.ititus.mymod.util.energy;

public class EnergyProducer extends EnergyStorageBase {

    public EnergyProducer(int capacity) {
        this(capacity, capacity);
    }

    public EnergyProducer(int capacity, int maxExtract) {
        this(capacity, maxExtract, 0);
    }

    public EnergyProducer(int capacity, int maxExtract, int energy) {
        super(capacity, 0, maxExtract, energy);
    }

}

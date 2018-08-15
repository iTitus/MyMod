package io.github.ititus.mymod.util.multi.energy;

import io.github.ititus.mymod.tile.TileEnergyCell;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import io.github.ititus.mymod.util.energy.EnergyStorageBaseCombinedWrapper;
import io.github.ititus.mymod.util.grid.BaseGrid;

import java.util.Objects;

public class EnergyCellGrid extends BaseGrid<TileEnergyCell> {

    private EnergyStorageBaseCombinedWrapper energyWrapper;

    @Override
    public void onGridChange() {
        energyWrapper = null;
    }

    public EnergyStorageBaseCombinedWrapper getEnergyStorage() {
        if (energyWrapper == null) {
            energyWrapper = new EnergyStorageBaseCombinedWrapper(
                    getMembers().
                            stream()
                            .filter(Objects::nonNull)
                            .filter(tile -> !tile.isInvalid())
                            .sorted(gridMemberComparator)
                            .map(TileEnergyCell::getEnergyStorageBase)
                            .toArray(EnergyStorageBase[]::new)
            );
        }
        return energyWrapper;
    }

}

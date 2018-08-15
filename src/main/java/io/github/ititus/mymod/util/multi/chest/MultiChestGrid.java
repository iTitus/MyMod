package io.github.ititus.mymod.util.multi.chest;

import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.inventory.ItemStackHandlerBaseCombinedWrapper;
import io.github.ititus.mymod.tile.TileMultiChest;
import io.github.ititus.mymod.util.grid.BaseGrid;

import java.util.Objects;

public class MultiChestGrid extends BaseGrid<TileMultiChest> {

    private ItemStackHandlerBaseCombinedWrapper inventoryWrapper;

    @Override
    public void onGridChange() {
        inventoryWrapper = null;
    }

    public ItemStackHandlerBaseCombinedWrapper getItemStackHandler() {
        if (inventoryWrapper == null) {
            inventoryWrapper = new ItemStackHandlerBaseCombinedWrapper(
                    getMembers().
                            stream()
                            .filter(Objects::nonNull)
                            .filter(tile -> !tile.isInvalid())
                            .sorted(gridMemberComparator)
                            .map(TileMultiChest::getInventoryBase)
                            .toArray(ItemStackHandlerBase[]::new)
            );
        }
        return inventoryWrapper;
    }

}

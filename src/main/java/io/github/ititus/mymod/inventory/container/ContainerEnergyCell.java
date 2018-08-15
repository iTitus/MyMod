package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.inventory.slot.SlotItemHandlerBase;
import io.github.ititus.mymod.tile.TileEnergyCell;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerEnergyCell extends ContainerBase<TileEnergyCell> {

    public ContainerEnergyCell(EntityPlayer player, TileEnergyCell tile) {
        super(player, tile);
        addPlayerInventory(8, 84);
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 0, 32, 29));
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 1, 32, 53));
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 2, 128, 29));
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 3, 128, 53));
    }

}

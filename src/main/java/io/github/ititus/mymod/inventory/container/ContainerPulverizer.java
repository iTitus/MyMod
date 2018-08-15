package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.inventory.slot.SlotItemHandlerBase;
import io.github.ititus.mymod.tile.TilePulverizer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerPulverizer extends ContainerBase<TilePulverizer> {

    public ContainerPulverizer(EntityPlayer player, TilePulverizer tile) {
        super(player, tile);
        addPlayerInventory(8, 84);
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 0, 30, 35));
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 1, 88, 35).setOutputOnly());
        addSlotToContainer(new SlotItemHandlerBase(tile.inventory, 2, 108, 35).setOutputOnly());
    }

}

package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.tile.TileTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerTank extends ContainerBase<TileTank> {

    public ContainerTank(EntityPlayer player, TileTank tile) {
        super(player, tile);
        addPlayerInventory(8, 84);
        addSlotToContainer(new SlotItemHandler(tile.inventory, 0, 32, 41));
        addSlotToContainer(new SlotItemHandler(tile.inventory, 1, 128, 41));
    }
}

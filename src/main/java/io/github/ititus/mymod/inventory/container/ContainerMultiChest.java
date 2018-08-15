package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.tile.TileMultiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMultiChest extends ContainerBase<TileMultiChest> {

    public ContainerMultiChest(EntityPlayer player, TileMultiChest tile) {
        super(player, tile);
        addPlayerInventory(8, 84);
        if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (handler != null) {
                int slots = handler.getSlots();
                int slotsXMax = 9;
                for (int i = 0; i < slots; i++) {
                    int x = i % slotsXMax;
                    int y = i / slotsXMax;
                    addSlotToContainer(new SlotItemHandler(handler, i, 8 + 18 * x, 17 + 18 * y));
                }
            }
        }
    }

}

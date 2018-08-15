package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.backpack.Backpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBackpack extends ContainerBase<TileBase> {

    private final ItemStack stack;
    private final Backpack backpack;

    public ContainerBackpack(EntityPlayer player, ItemStack stack, Backpack backpack) {
        super(player, null);
        this.stack = stack;
        this.backpack = backpack;
        addPlayerInventory(8, 84);
        int slots = backpack.getItemHandler().getSlots();
        int slotsXMax = 9;
        for (int i = 0; i < slots; i++) {
            int x = i % slotsXMax;
            int y = i / slotsXMax;
            addSlotToContainer(new SlotItemHandler(backpack.getItemHandler(), i, 8 + 18 * x, 17 + 18 * y));
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public Backpack getBackpack() {
        return backpack;
    }
}

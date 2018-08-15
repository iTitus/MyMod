package io.github.ititus.mymod.inventory;

import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class ItemStackHandlerBaseWrapper extends ItemStackHandlerBase {

    protected final IItemHandlerModifiable inventory;

    public ItemStackHandlerBaseWrapper(IItemHandlerModifiable inventory, TileBase tile) {
        super(inventory.getSlots(), tile);
        this.inventory = inventory;
    }

    @Override
    public int getSlots() {
        return inventory.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return canInsert(stack, slot) ? inventory.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return canExtract(slot) ? inventory.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getSlotLimit(slot);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return inventory.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        return inventory.extractItem(slot, amount, simulate);
    }

}

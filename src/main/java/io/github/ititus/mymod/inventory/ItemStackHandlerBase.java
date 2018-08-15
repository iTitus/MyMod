package io.github.ititus.mymod.inventory;


import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerBase extends ItemStackHandler {

    protected final TileBase tile;

    public ItemStackHandlerBase(TileBase tile) {
        super();
        this.tile = tile;
    }

    public ItemStackHandlerBase(int size, TileBase tile) {
        super(size);
        this.tile = tile;
    }

    public ItemStackHandlerBase(NonNullList<ItemStack> stacks, TileBase tile) {
        super(stacks);
        this.tile = tile;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return canInsert(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return canExtract(slot) ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Nonnull
    public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    public boolean canInsert(ItemStack stack, int slot) {
        return true;
    }

    public boolean canInsertIntoSlot(ItemStack stack, int slot) {
        return canInsert(stack, slot);
    }

    public boolean canExtract(int slot) {
        return true;
    }

    public boolean canExtractFromSlot(int slot) {
        return canExtract(slot);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (tile != null) {
            tile.markDirty();
        }
    }

    public TileBase getTile() {
        return tile;
    }
}

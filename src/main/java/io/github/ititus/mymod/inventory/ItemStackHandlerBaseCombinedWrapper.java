package io.github.ititus.mymod.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class ItemStackHandlerBaseCombinedWrapper extends ItemStackHandlerBase {

    protected final ItemStackHandlerBase[] inventories;
    protected final int[] baseIndex;
    protected final int slotCount;

    public ItemStackHandlerBaseCombinedWrapper(ItemStackHandlerBase... inventories) {
        super(0, null);
        this.inventories = inventories;
        this.baseIndex = new int[inventories.length];
        int index = 0;
        for (int i = 0; i < inventories.length; i++) {
            index += inventories[i].getSlots();
            baseIndex[i] = index;
        }
        this.slotCount = index;
    }

    protected int getIndexForSlot(int slot) {
        if (slot < 0)
            return -1;

        for (int i = 0; i < baseIndex.length; i++) {
            if (slot - baseIndex[i] < 0) {
                return i;
            }
        }
        return -1;
    }

    protected ItemStackHandlerBase getInventoryFromIndex(int index) {
        if (index < 0 || index >= inventories.length) {
            return null;
        }
        return inventories[index];
    }

    protected int getSlotFromIndex(int slot, int index) {
        if (index <= 0 || index >= baseIndex.length) {
            return slot;
        }
        return slot - baseIndex[index - 1];
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        inventory.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return slotCount;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        int localSlot = getSlotFromIndex(slot, index);
        return inventory.getSlotLimit(localSlot);
    }

    @Nonnull
    @Override
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.insertItemInternal(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.extractItemInternal(slot, amount, simulate);
    }

    @Override
    public boolean canInsert(ItemStack stack, int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.canInsert(stack, slot);
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.canInsertIntoSlot(stack, slot);
    }

    @Override
    public boolean canExtract(int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.canExtract(slot);
    }

    @Override
    public boolean canExtractFromSlot(int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        return inventory.canExtractFromSlot(slot);
    }

    @Override
    protected void onContentsChanged(int slot) {
        int index = getIndexForSlot(slot);
        ItemStackHandlerBase inventory = getInventoryFromIndex(index);
        slot = getSlotFromIndex(slot, index);
        inventory.onContentsChanged(slot);
    }

    @Override
    public void setSize(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            throw new IllegalArgumentException("Slot " + slot + " not in valid range - [0, " + getSlots() + ")");
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void onLoad() {
        throw new UnsupportedOperationException();
    }

}

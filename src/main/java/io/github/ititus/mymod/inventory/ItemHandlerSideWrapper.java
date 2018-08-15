package io.github.ititus.mymod.inventory;

import com.google.common.primitives.Ints;
import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.side.SideConfiguration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ItemHandlerSideWrapper implements IItemHandler {

    protected final Supplier<ItemStackHandlerBase> inventory;
    protected final EnumFacing facing;

    public ItemHandlerSideWrapper(Supplier<ItemStackHandlerBase> inventory, EnumFacing facing) {
        this.inventory = inventory;
        this.facing = facing;
    }

    @Override
    public int getSlots() {
        return inventory.get().getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.get().getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStackHandlerBase inv = inventory.get();
        TileBase tile = inv.getTile();
        SideConfiguration cfg = tile != null ? tile.getSideConfiguration() : null;
        int[] slots = tile != null ? tile.getInputSlots() : null;
        return (slots == null || Ints.contains(slots, slot)) && (cfg == null || cfg.canInsert(facing, slot)) ? inv.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStackHandlerBase inv = inventory.get();
        TileBase tile = inv.getTile();
        SideConfiguration cfg = tile != null ? tile.getSideConfiguration() : null;
        int[] slots = tile != null ? tile.getOutputSlots() : null;
        return (slots == null || Ints.contains(slots, slot)) && (cfg == null || cfg.canExtract(facing, slot)) ? inv.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.get().getSlotLimit(slot);
    }
}

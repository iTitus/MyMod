package io.github.ititus.mymod.inventory;

import io.github.ititus.mymod.util.backpack.Backpack;

public class ItemHandlerBackpack extends ItemStackHandlerBase {

    private final Backpack backpack;

    public ItemHandlerBackpack(int size, Backpack backpack) {
        super(size, null);
        this.backpack = backpack;
    }

    @Override
    protected void onContentsChanged(int slot) {
        backpack.markDirty();
    }
}

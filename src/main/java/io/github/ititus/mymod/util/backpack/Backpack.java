package io.github.ititus.mymod.util.backpack;

import io.github.ititus.mymod.inventory.ItemHandlerBackpack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class Backpack implements INBTSerializable<NBTTagCompound> {

    private final BackpackManager backpackManager;
    private final ItemHandlerBackpack itemHandler;

    public Backpack(BackpackManager backpackManager) {
        this.backpackManager = backpackManager;
        this.itemHandler = new ItemHandlerBackpack(27, this);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("inventory", itemHandler.serializeNBT());
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    public ItemHandlerBackpack getItemHandler() {
        return itemHandler;
    }

    public void markDirty() {
        backpackManager.markDirty();
    }
}

package io.github.ititus.mymod.inventory.slot;

import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotItemHandlerBase extends SlotItemHandler {

    private boolean outputOnly, inputOnly;

    public SlotItemHandlerBase(ItemStackHandlerBase itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.outputOnly = false;
        this.inputOnly = false;
    }

    public SlotItemHandlerBase setOutputOnly() {
        this.outputOnly = true;
        return this;
    }

    public SlotItemHandlerBase setInputOnly() {
        this.inputOnly = true;
        return this;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return !inputOnly && getItemHandler().canExtractFromSlot(getSlotIndex());
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return !outputOnly && getItemHandler().canInsertIntoSlot(stack, getSlotIndex());
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int amount) {
        return getItemHandler().canExtractFromSlot(getSlotIndex()) ? getItemHandler().extractItemInternal(getSlotIndex(), amount, false) : ItemStack.EMPTY;
    }

    @Override
    public ItemStackHandlerBase getItemHandler() {
        return (ItemStackHandlerBase) super.getItemHandler();
    }
}

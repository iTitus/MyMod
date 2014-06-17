package io.github.iTitus.MyMod.tileentity.wheel;

import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileEntityWheel extends MyTileEntity implements ISidedInventory {

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		return null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
	}

}

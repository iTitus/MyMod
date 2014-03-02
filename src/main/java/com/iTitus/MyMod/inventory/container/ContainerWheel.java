package com.iTitus.MyMod.inventory.container;

import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWheel extends MyContainer {

	private TileEntityWheel wheel;

	public ContainerWheel(InventoryPlayer inventory, TileEntityWheel wheel) {
		super(inventory, true);
		this.wheel = wheel;
		this.wheel.openInventory();

		addSlots();
	}

	protected void addSlots() {
		addSlotToContainer(new Slot(wheel, 0, 31, 35));
		addSlotToContainer(new Slot(wheel, 1, 80, 35));
		addSlotToContainer(new Slot(wheel, 2, 129, 35));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		// TODO: Shift-Clicking
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return wheel.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		wheel.closeInventory();
	}

}

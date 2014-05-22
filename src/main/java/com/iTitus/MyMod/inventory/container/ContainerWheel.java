package com.iTitus.MyMod.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.iTitus.MyMod.tileentity.wheel.TileEntityWheel;

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
	public boolean canInteractWith(EntityPlayer player) {
		return wheel.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		wheel.closeInventory();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		System.out.println("Slot -> " + slotID);
		ItemStack retStack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			retStack = stack.copy();

			System.out.println(slotID + " - "
					+ (inventorySlots.size() - wheel.getSizeInventory()));
			if (slotID >= (inventorySlots.size() - wheel.getSizeInventory())) {
				System.out.println("Im Wheel!");
				if (!mergeItemStack(stack,
						(inventorySlots.size() - wheel.getSizeInventory() - 9),
						(inventorySlots.size() - wheel.getSizeInventory()),
						false)
						&& !mergeItemStack(stack, 0, (inventorySlots.size()
								- wheel.getSizeInventory() - 9), false)) {
					return null;
				}
			} else if (!mergeItemStack(stack,
					(inventorySlots.size() - wheel.getSizeInventory()),
					inventorySlots.size(), false)) {
				return null;
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return retStack;
	}

}

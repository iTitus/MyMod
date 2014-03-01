package com.iTitus.MyMod.inventory.container;

import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerWheel extends MyContainer {

	private TileEntityWheel wheel;

	public ContainerWheel(InventoryPlayer inventory, TileEntityWheel wheel) {
		super(inventory, true);
		this.wheel = wheel;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return wheel.isUseableByPlayer(player);
	}

}

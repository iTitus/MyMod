package com.iTitus.MyMod.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import com.iTitus.MyMod.item.ModItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class CraftingHandler {

	public static void init() {
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
	}

	@SubscribeEvent
	public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent event) {

		if (event.crafting.isItemEqual(new ItemStack(ModItems.ammo))) {
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = event.craftMatrix.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof ItemSword) {

					if (stack.getItemDamage() <= stack.getMaxDamage() - 1) {
						stack.setItemDamage(stack.getItemDamage() + 1);
						stack.stackSize++;
					} else {
						event.craftMatrix.setInventorySlotContents(i, null);
						MinecraftForge.EVENT_BUS
								.post(new PlayerDestroyItemEvent(event.player,
										stack));
					}
				}
			}
		}
	}

}

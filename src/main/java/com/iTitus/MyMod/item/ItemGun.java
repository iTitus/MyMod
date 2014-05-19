package com.iTitus.MyMod.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemGun extends MyItem {

	public ItemGun() {
		super(EnumItemType.gun);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(StatCollector.translateToLocal("lore.gun"));
	}

}

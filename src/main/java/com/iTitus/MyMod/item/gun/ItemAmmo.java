package com.iTitus.MyMod.item.gun;

import java.util.List;

import com.iTitus.MyMod.item.EnumItemType;
import com.iTitus.MyMod.item.MyItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemAmmo extends MyItem {

	public ItemAmmo() {
		super(EnumItemType.ammo);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(StatCollector.translateToLocal("lore.ammo"));
		list.add("");
		if (getModifiers(stack) != null) {
			for (Item i : getModifiers(stack)) {
				list.add(StatCollector.translateToLocal(i.getUnlocalizedName()
						+ ".name"));
			}
		}

	}

	private Item[] getModifiers(ItemStack stack) {
		return new Item[] { Items.gunpowder, Items.gunpowder, Items.diamond };
	}

}

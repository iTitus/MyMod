package com.iTitus.MyMod.item.gun;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import com.iTitus.MyMod.helper.NBTHelper;
import com.iTitus.MyMod.item.EnumItemType;
import com.iTitus.MyMod.item.MyItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAmmo extends MyItem {

	public static final String TAG_MODIFIER = "modifier",
			TAG_MODIFIER_ID = "modifier_ID",
			TAG_MODIFIER_COUNT = "modifier_count";

	public ItemAmmo() {
		super(EnumItemType.ammo);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(StatCollector.translateToLocal("lore.ammo"));

		if (NBTHelper.hasNBT(stack)) {
			list.add("");

			HashMap<EnumModifierType, Integer> modifiers = readFromNBT(stack
					.getTagCompound());

			for (EnumModifierType modifier : modifiers.keySet()) {
				list.add(modifiers.get(modifier) + "x "
						+ modifier.getItemStack().getDisplayName());
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);

		HashMap<EnumModifierType, Integer> modifiers = new HashMap<EnumModifierType, Integer>();
		for (EnumModifierType modifier : EnumModifierType.values()) {
			ItemStack stack = new ItemStack(item);
			modifiers.put(modifier, 1);
			stack.setTagCompound(writeToNBT(modifiers, new NBTTagCompound()));
			list.add(stack);
			modifiers.clear();
		}

	}

	public static NBTTagCompound writeToNBT(
			HashMap<EnumModifierType, Integer> modifiers, NBTTagCompound nbt) {

		if (modifiers == null)
			return nbt;

		merge(modifiers, readFromNBT(nbt));

		NBTTagList tags = new NBTTagList();

		for (EnumModifierType modifier : modifiers.keySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_MODIFIER_ID, modifier.name());
			tag.setInteger(TAG_MODIFIER_COUNT, modifiers.get(modifier));
			tags.appendTag(tag);
		}

		nbt.setTag(TAG_MODIFIER, tags);

		return nbt;
	}

	public static HashMap<EnumModifierType, Integer> readFromNBT(
			NBTTagCompound nbt) {

		HashMap<EnumModifierType, Integer> modifiers = new HashMap<EnumModifierType, Integer>();

		if (nbt == null || !nbt.hasKey(TAG_MODIFIER))
			return modifiers;

		NBTTagList tags = nbt.getTagList(TAG_MODIFIER, 10);

		for (int i = 0; i < tags.tagCount(); ++i) {
			NBTTagCompound tag = tags.getCompoundTagAt(i);
			modifiers.put(
					EnumModifierType.valueOf(tag.getString(TAG_MODIFIER_ID)),
					tag.getInteger(TAG_MODIFIER_COUNT));
		}

		return modifiers;
	}

	public static void merge(HashMap<EnumModifierType, Integer> to,
			HashMap<EnumModifierType, Integer> from) {

		for (EnumModifierType modifier : from.keySet()) {
			if (to.containsKey(modifier)) {
				int count = from.get(modifier) + to.get(modifier);
				to.remove(modifier);
				to.put(modifier, count);
			} else {
				to.put(modifier, from.get(modifier));
			}
		}

	}

	public static boolean isValidAmmo(
			HashMap<EnumModifierType, Integer> modifiers) {

		for (EnumModifierType modifier : modifiers.keySet()) {
			if (modifier.getMaxCount() != 0
					&& modifiers.get(modifier) > modifier.getMaxCount()) {
				return false;
			}
		}

		return true;
	}
}

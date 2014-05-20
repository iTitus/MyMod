package com.iTitus.MyMod.item.gun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
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
			for (Modifier modifier : readFromNBT(stack.getTagCompound())) {
				list.add(StatCollector.translateToLocal(modifier
						.getModifierType().getItemStack().getUnlocalizedName()
						+ ".name")
						+ " - "
						+ modifier.getModifierType().getItemStack()
								.getDisplayName());
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		ItemStack stack = new ItemStack(item);
		stack.setTagCompound(ItemAmmo.writeToNBT(
				new ArrayList<Modifier>(Arrays.asList(new Modifier[] {
						new Modifier(EnumModifierType.gunpowder, 2),
						new Modifier(EnumModifierType.tnt, 1) })),
				new NBTTagCompound()));
		list.add(stack);
	}

	public static NBTTagCompound writeToNBT(ArrayList<Modifier> modifiers,
			NBTTagCompound nbt) {

		NBTTagList tags = new NBTTagList();
		for (Modifier modifier : modifiers) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_MODIFIER_ID, modifier.getModifierType().name());
			tag.setInteger(TAG_MODIFIER_COUNT, modifier.getCount());
			tags.appendTag(tag);
		}
		nbt.setTag(TAG_MODIFIER, tags);

		return nbt;
	}

	public static ArrayList<Modifier> readFromNBT(NBTTagCompound nbt) {

		ArrayList<Modifier> list = new ArrayList<Modifier>();
		NBTTagList tags = nbt.getTagList(TAG_MODIFIER, 10);

		for (int i = 0; i < tags.tagCount(); ++i) {
			NBTTagCompound tag = tags.getCompoundTagAt(i);
			list.add(new Modifier(EnumModifierType.valueOf(tag
					.getString(TAG_MODIFIER_ID)), tag
					.getInteger(TAG_MODIFIER_COUNT)));
		}

		return list;
	}
}

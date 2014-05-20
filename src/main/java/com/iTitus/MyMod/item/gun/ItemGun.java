package com.iTitus.MyMod.item.gun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iTitus.MyMod.entity.EntityBullet;
import com.iTitus.MyMod.item.EnumItemType;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.item.MyItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemGun extends MyItem {

	public ItemGun() {
		super(EnumItemType.gun);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(StatCollector.translateToLocal("lore.gun"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {

		if (!world.isRemote) {

			// TODO: Search for ammo in inventory
			// ----
			ItemStack ammo = new ItemStack(ModItems.ammo);
			ammo.setTagCompound(ItemAmmo.writeToNBT(
					new ArrayList<Modifier>(Arrays.asList(new Modifier[] {
							new Modifier(EnumModifierType.gunpowder, 2),
							new Modifier(EnumModifierType.tnt, 1) })),
					new NBTTagCompound()));
			// -----

			EntityBullet bullet = new EntityBullet(world, player,
					ItemAmmo.readFromNBT(ammo.getTagCompound()));
			world.spawnEntityInWorld(bullet);
		}

		return stack;
	}
}

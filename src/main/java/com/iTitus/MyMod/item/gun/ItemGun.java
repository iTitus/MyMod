package com.iTitus.MyMod.item.gun;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.iTitus.MyMod.client.particle.EntityBulletCasingFX;
import com.iTitus.MyMod.entity.EntityBullet;
import com.iTitus.MyMod.item.EnumItemType;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.item.MyItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

		ItemStack ammo = null;

		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ammo = player.inventory.getStackInSlot(i);
			if (ammo == null || !ammo.getItem().equals(ModItems.ammo))
				continue;
			else {

				if (!player.capabilities.isCreativeMode)
					player.inventory.decrStackSize(i, 1);

				if (!world.isRemote) {
					world.spawnEntityInWorld(new EntityBullet(world, player,
							ItemAmmo.readFromNBT(ammo.getTagCompound())));
					world.spawnEntityInWorld(EntityBulletCasingFX
							.makeCasing(player));
				}

				break;
			}

		}

		return stack;
	}
}

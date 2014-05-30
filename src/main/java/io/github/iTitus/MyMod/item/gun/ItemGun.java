package io.github.iTitus.MyMod.item.gun;

import io.github.iTitus.MyMod.entity.gun.EntityBullet;
import io.github.iTitus.MyMod.item.EnumItemType;
import io.github.iTitus.MyMod.item.ModItems;
import io.github.iTitus.MyMod.item.MyItem;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGun extends MyItem {

	public ItemGun() {
		super(EnumItemType.GUN);
		setMaxStackSize(1);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
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
							ItemAmmo.readFromNBT(ammo.getTagCompound()))
							.onShoot());
					// world.spawnEntityInWorld(new EntityBulletCasing(player));
				}

				else {
					for (int j = 0; i < 8; i++) {
						world.spawnParticle("largesmoke", player.posX,
								player.posY, player.posZ, 0.0D, 0.0D, 0.0D);
					}
					player.playSound("mob.wither.shoot", 1.0F, 1.0F);
				}

				break;
			}

		}

		return stack;
	}
}

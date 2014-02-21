package com.iTitus.MyMod.item;

import com.iTitus.MyMod.lib.MyCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class MyItem extends Item {

	public MyItem(EnumItemType type) {
		super();
		setUnlocalizedName(type.name);
		if (putInTab())
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	public abstract boolean putInTab();

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		super.registerIcons(register);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return super.getIcon(stack, pass);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		return super.getIconFromDamage(dmg);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		return super
				.getIcon(stack, renderPass, player, usingItem, useRemaining);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int renderPass) {
		return super.getIconFromDamageForRenderPass(dmg, renderPass);
	}

}

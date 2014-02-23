package com.iTitus.MyMod.item;

import com.iTitus.MyMod.lib.LibTextures;
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
		setTextureName(LibTextures.getTextureLoc(type));
		if (putInTab())
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	public abstract boolean putInTab();

}

package com.iTitus.MyMod.client.render.item;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import com.iTitus.MyMod.client.model.ModelGun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemGunRenderer implements IItemRenderer {

	public static final ItemGunRenderer INSTANCE = new ItemGunRenderer();

	public static ModelGun model;

	private ItemGunRenderer() {
		model = new ModelGun();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		model.renderAll();

	}

}

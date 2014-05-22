package com.iTitus.MyMod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelGun extends ModelBase {

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {

	}

	public void renderAll() {
		render(null, 0, 0, 0, 0, 0, 0);
	}

}

package com.iTitus.MyMod.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelWheel extends ModelBase {

	ModelRenderer Base;
	ModelRenderer Holder;
	ModelRenderer Wheel;

	public ModelWheel() {
		textureWidth = 64;
		textureHeight = 32;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(0F, 0F, 0F, 4, 10, 4);
		Base.setRotationPoint(-2F, 14F, 2F);
		Base.setTextureSize(64, 32);
		Holder = new ModelRenderer(this, 44, 0);
		Holder.addBox(0F, 0F, 0F, 2, 2, 1);
		Holder.setRotationPoint(-1F, 15F, 1F);
		Holder.setTextureSize(64, 32);
		Wheel = new ModelRenderer(this, 16, 0);
		Wheel.addBox(-6F, -6F, 0F, 12, 12, 1);
		Wheel.setRotationPoint(0F, 16F, 0F);
		Wheel.setTextureSize(64, 32);

	}

	public void render(float rotationAngle) {
		float f = 1F / 16F;
		Base.render(f);
		Holder.render(f);

		Wheel.rotateAngleZ = rotationAngle;
		Wheel.render(f);
	}

}

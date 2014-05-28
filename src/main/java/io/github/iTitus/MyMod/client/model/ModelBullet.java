package io.github.iTitus.MyMod.client.model;

import org.lwjgl.opengl.GL11;

import io.github.iTitus.MyMod.lib.LibModels;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBullet {

	private IModelCustom model;
	private static float factor = 1F / 16F;

	public ModelBullet() {
		model = AdvancedModelLoader.loadModel(LibModels.Models.BULLET
				.getModelResoureLoc());
	}

	public void render() {
		GL11.glScalef(factor, factor, factor);
		model.renderAll();
	}

}

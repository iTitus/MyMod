package io.github.iTitus.MyMod.client.render.entity;

import io.github.iTitus.MyMod.lib.LibModels;
import io.github.iTitus.MyMod.lib.LibTextures;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBullet extends Render {

	public static final RenderEntityBullet INSTANCE = new RenderEntityBullet();
	private static final IModelCustom model = AdvancedModelLoader
			.loadModel(LibModels.Models.BULLET.getModelResoureLoc());

	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float rotaionYaw, float ticks) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glTranslated(x, y, z);
		GL11.glScalef(1F / 32F, 1F / 32F, 1F / 32F);

		bindEntityTexture(entity);

		model.renderAll();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return LibTextures.getModelTextureResourceLoc(LibModels.Models.BULLET);
	}

}

package io.github.iTitus.MyMod.client.render.entity;

import io.github.iTitus.MyMod.client.model.ModelBullet;
import io.github.iTitus.MyMod.lib.LibModels;
import io.github.iTitus.MyMod.lib.LibTextures;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderEntityBullet extends Render {

	public static final RenderEntityBullet INSTANCE = new RenderEntityBullet();
	private static ModelBullet model;

	private RenderEntityBullet() {
		model = new ModelBullet();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float var8, float ticks) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		// GL11.glRotatef(180, 1, 0, 0);
		// GL11.glTranslatef(0, 0.9F, 0);

		bindEntityTexture(entity);

		model.render();

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return LibTextures.getModelTextureResourceLoc(LibModels.Models.BULLET);
	}

}

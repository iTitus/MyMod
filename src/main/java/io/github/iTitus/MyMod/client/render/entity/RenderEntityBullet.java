package io.github.iTitus.MyMod.client.render.entity;

import io.github.iTitus.MyMod.client.model.ModelBullet;
import io.github.iTitus.MyMod.lib.LibModels;
import io.github.iTitus.MyMod.lib.LibTextures;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBullet extends Render {

	public static final RenderEntityBullet INSTANCE = new RenderEntityBullet();
	private static final ResourceLocation texture = LibTextures
			.getModelTextureResourceLoc(LibModels.Models.BULLET);
	private static final ModelBullet model = new ModelBullet();

	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float var8, float ticks) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		bindEntityTexture(entity);

		model.render();

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

}

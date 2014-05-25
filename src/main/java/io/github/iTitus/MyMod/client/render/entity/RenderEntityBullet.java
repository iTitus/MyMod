package io.github.iTitus.MyMod.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityBullet extends Render {

	public static final RenderEntityBullet INSTANCE = new RenderEntityBullet();

	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float var8, float ticks) {

		System.out.println(var8);

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}

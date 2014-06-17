package io.github.iTitus.MyMod.client.render.tileentity;

import io.github.iTitus.MyMod.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntitySphere extends TileEntitySpecialRenderer {

	public static final RenderTileEntitySphere INSTANCE = new RenderTileEntitySphere();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float partialTicks) {

		float radius = 0.75F;

		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);

		GL11.glColor4f(1F, 1F, 1F, 1F);

		double posX = Minecraft.getMinecraft().renderViewEntity.lastTickPosX
				+ (Minecraft.getMinecraft().renderViewEntity.posX - Minecraft
						.getMinecraft().renderViewEntity.lastTickPosX)
				* partialTicks;
		double posY = Minecraft.getMinecraft().renderViewEntity.lastTickPosY
				+ (Minecraft.getMinecraft().renderViewEntity.posY - Minecraft
						.getMinecraft().renderViewEntity.lastTickPosY)
				* partialTicks;
		double posZ = Minecraft.getMinecraft().renderViewEntity.lastTickPosZ
				+ (Minecraft.getMinecraft().renderViewEntity.posZ - Minecraft
						.getMinecraft().renderViewEntity.lastTickPosZ)
				* partialTicks;

		double translation = MathUtil
				.sin((Minecraft.getMinecraft().renderViewEntity.ticksExisted + partialTicks) / 10.0F) * 0.1F + 0.1F;

		GL11.glTranslated(-posX, -posY, -posZ);

		GL11.glBegin(GL11.GL_LINE_LOOP);

		for (float angle1 = 0; angle1 <= 180; angle1++) {

			for (float angle2 = 0; angle2 < 360; angle2++) {

				GL11.glVertex3d(
						radius * MathUtil.sin(MathUtil.degToRad(angle1))
								* MathUtil.cos(MathUtil.degToRad(angle2))
								+ tile.xCoord + 0.5,
						radius * MathUtil.sin(MathUtil.degToRad(angle1))
								* MathUtil.sin(MathUtil.degToRad(angle2))
								+ tile.yCoord + 1.5 + translation, radius
								* MathUtil.cos(MathUtil.degToRad(angle1))
								+ tile.zCoord + 0.5);

			}

		}

		GL11.glEnd();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();

	}
}

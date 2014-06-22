package io.github.iTitus.MyMod.client.render.tileentity;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.lib.LibMod;
import io.github.iTitus.MyMod.lib.LibTextures;
import io.github.iTitus.MyMod.tileentity.display.TileEntityDisplay;
import io.github.iTitus.MyMod.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderTileEntityDisplay extends TileEntitySpecialRenderer {

	public static final TileEntitySpecialRenderer INSTANCE = new RenderTileEntityDisplay();

	private static final ResourceLocation texture = new ResourceLocation(
			LibMod.MOD_ID, LibTextures.getTextureLoc(EnumBlockType.DISPLAY));

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float partialTicks) {

		TileEntityDisplay display = (TileEntityDisplay) tile;

		GL11.glPushMatrix();

		int angle = 0;

		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

		switch (display.getOrientation()) {
		case NORTH:
			angle = 180;
			break;
		case SOUTH:
			break;
		case WEST:
			angle = 90;
			break;
		case EAST:
			angle = -90;
			break;
		default:
			break;
		}

		GL11.glRotated(-angle, 0, 1, 0);

		if (display.isStanding()) {
			GL11.glTranslated(0, 0, 0.25);
			GL11.glRotated(-45, 1, 0, 0);
		} else if (display.isHanging()) {
			GL11.glTranslated(0, -1 / 16D, 0.25);
			GL11.glRotated(45, 1, 0, 0);
		}

		GL11.glTranslated(0, 1 / 48D, -524 / 1200D);

		GL11.glScaled(1 / 90D, -1 / 90D, 1 / 90D);
		GL11.glNormal3d(0, 0, -1 / 90D);
		GL11.glDepthMask(false);

		for (int i = 0; i < display.getText().length; i++) {
			String s = display.getText()[i];

			RenderUtil.FR.drawString(s, -RenderUtil.FR.getStringWidth(s) / 2, i
					* 10 - display.getText().length * 5, 0);

		}

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}

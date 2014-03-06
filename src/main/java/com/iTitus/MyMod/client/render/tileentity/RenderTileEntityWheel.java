package com.iTitus.MyMod.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.client.model.ModelWheel;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntityWheel extends TileEntitySpecialRenderer {

	public static final TileEntitySpecialRenderer INSTANCE = new RenderTileEntityWheel();

	@SideOnly(Side.CLIENT)
	private final ModelWheel model;
	@SideOnly(Side.CLIENT)
	private final ResourceLocation texture;

	public RenderTileEntityWheel() {
		model = new ModelWheel();
		texture = new ResourceLocation(LibTextures.TEXTURE_LOCATION,
				LibTextures.getModelTextureLoc(EnumBlockType.WHEEL));
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float tick) {

		TileEntityWheel wheel = (TileEntityWheel) tile;

		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GL11.glScalef(1F, -1F, -1F);
		ForgeDirection direction = wheel.getOrientation();
		short angle = 0;
		if (direction != null) {
			if (direction == ForgeDirection.NORTH) {
				angle = 180;
			} else if (direction == ForgeDirection.SOUTH) {
				angle = 0;
			} else if (direction == ForgeDirection.WEST) {
				angle = 90;
			} else if (direction == ForgeDirection.EAST) {
				angle = -90;
			}
		}
		GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
		model.render(wheel.getRotationAngleRad());
		GL11.glPopMatrix();

	}
}

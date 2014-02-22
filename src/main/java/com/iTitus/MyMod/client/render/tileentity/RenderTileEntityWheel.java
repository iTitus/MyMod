package com.iTitus.MyMod.client.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.client.model.ModelWheel;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

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
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(x, y, z);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GL11.glTranslatef(0.5F, 1.5F, 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		model.render(wheel.getRotationAngleRad());
		GL11.glScalef(1F, -1F, -1F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		Minecraft.getMinecraft().renderEngine
				.bindTexture(TextureMap.locationBlocksTexture);

	}
}

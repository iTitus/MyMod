package com.iTitus.MyMod.client.render.block;

import org.lwjgl.opengl.GL11;

import com.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockWheel implements ISimpleBlockRenderingHandler {

	public static final ISimpleBlockRenderingHandler INSTANCE = new RenderBlockWheel();

	@Override
	public int getRenderId() {
		return LibRender.WHEEL_ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		new RenderTileEntityWheel().renderTileEntityAt(new TileEntityWheel(),
				0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}

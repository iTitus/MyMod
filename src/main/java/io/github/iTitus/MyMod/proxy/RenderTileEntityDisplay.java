package io.github.iTitus.MyMod.proxy;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTileEntityDisplay extends TileEntitySpecialRenderer {

	public static final TileEntitySpecialRenderer INSTANCE = new RenderTileEntityDisplay();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float partialTicks) {
	}

}

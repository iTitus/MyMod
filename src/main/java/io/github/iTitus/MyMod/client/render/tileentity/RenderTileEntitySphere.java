package io.github.iTitus.MyMod.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTileEntitySphere extends TileEntitySpecialRenderer {

	public static final RenderTileEntitySphere INSTANCE = new RenderTileEntitySphere();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float ticks) {
		
		/* Spherical coordinates ;) Have to be drawn by GL
		 * 
		 * x = "radius" * sin("Angle1") * cos("Angle2")
		 * y = "radius" * sin("Angle1") * sin("Angle2")
		 * z = "radius" * cos("Angle1")
		 * 
		 * "Angle1" = [0;"pi"] / [0°;180°]
		 * "Angle2" = [0;2"pi"[ / [0°;360°[ OR ]-"pi";"pi"] / ]-180°;180°]
		 */

	}

}

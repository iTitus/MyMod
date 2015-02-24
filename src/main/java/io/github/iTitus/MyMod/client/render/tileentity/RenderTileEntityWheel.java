package io.github.iTitus.MyMod.client.render.tileentity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.iTitus.MyMod.client.model.ModelWheel;
import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.lib.LibTextures;
import io.github.iTitus.MyMod.common.tileentity.wheel.TileEntityWheel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileEntityWheel extends TileEntitySpecialRenderer {

    public static final TileEntitySpecialRenderer INSTANCE = new RenderTileEntityWheel();

    private final ModelWheel model;
    private final ResourceLocation texture;

    public RenderTileEntityWheel() {
        model = new ModelWheel();
        texture = LibTextures.getModelResourceLoc(EnumBlockType.WHEEL);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
                                   double z, float tick) {

        TileEntityWheel wheel = (TileEntityWheel) tile;

        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        FMLClientHandler.instance().getClient().renderEngine
                .bindTexture(texture);
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
        model.render(0);
        GL11.glPopMatrix();

    }
}

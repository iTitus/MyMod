package io.github.iTitus.MyMod.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import io.github.iTitus.MyMod.common.lib.LibRender;
import io.github.iTitus.MyMod.common.tileentity.wheel.TileEntityWheel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlockWheel implements ISimpleBlockRenderingHandler {

    public static final RenderBlockWheel INSTANCE = new RenderBlockWheel();

    @Override
    public int getRenderId() {
        return LibRender.WHEEL_ID;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId,
                                     RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        RenderTileEntityWheel.INSTANCE.renderTileEntityAt(
                new TileEntityWheel(), 0.0D, 0.0D, 0.0D, 0.0F);
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

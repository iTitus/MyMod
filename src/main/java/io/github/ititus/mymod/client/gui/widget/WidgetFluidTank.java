package io.github.ititus.mymod.client.gui.widget;

import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.fluid.FluidTankBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class WidgetFluidTank extends WidgetBase {
    private static final int WIDTH = 18, HEIGHT = 66;
    private static final int U = 0, V = 66;
    private static final int U_2 = 18, V_2 = 64;

    private final FluidTankBase tank;

    public WidgetFluidTank(int x, int y, FluidTankBase tank) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
        this.tank = tank;
    }

    private static void setGLColorFromInt(int color) {
        float a = (color >> 24 & 0xFF) / 255F;
        float r = (color >> 16 & 0xFF) / 255F;
        float g = (color >> 8 & 0xFF) / 255F;
        float b = (color & 0xFF) / 255F;
        GlStateManager.color(r, g, b, a);
    }

    private static void drawFluidTexture(double x, double y, TextureAtlasSprite sprite, int maskTop, int maskRight, double zLevel) {
        double uMin = (double) sprite.getMinU();
        double uMax = (double) sprite.getMaxU();
        double vMin = (double) sprite.getMinV();
        double vMax = (double) sprite.getMaxV();
        //uMax = uMax - (maskRight / 16.0 * (uMax - uMin));
        //vMax = vMax - (maskTop / 16.0 * (vMax - vMin));
        uMin = uMin + (maskRight / 16.0 * (uMax - uMin));
        vMin = vMin + (maskTop / 16.0 * (vMax - vMin));

        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + 16, zLevel).tex(uMin, vMax).endVertex();
        buffer.pos(x + 16 - maskRight, y + 16, zLevel).tex(uMax, vMax).endVertex();
        buffer.pos(x + 16 - maskRight, y + maskTop, zLevel).tex(uMax, vMin).endVertex();
        buffer.pos(x, y + maskTop, zLevel).tex(uMin, vMin).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(partialTicks, mouseX, mouseY);
        float z = zLevel;
        zLevel = 200;
        drawTexturedModalRect(gui.getGuiLeft() + bounds.getX() + 1, gui.getGuiTop() + bounds.getY() + 1, bounds.getWidth() - 2, bounds.getHeight() - 2, U_2, V_2, texWidth - 2, texHeight - 2, SHEET_WIDTH, SHEET_HEIGHT);
        zLevel = z;
        drawFluid(tank.getFluid(), MathUtil.scaledClamp(tank.getFluidAmount(), tank.getCapacity(), bounds.getHeight() - 2));
    }

    @Override
    public void addInformation(List<String> tooltip, boolean advanced) {
        FluidStack fluid = tank.getFluid();
        tooltip.add(fluid != null ? fluid.getLocalizedName() : I18n.translateToLocal("text.mymod.fluid.empty"));
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("text.mymod.fluid.stored", fluid != null ? fluid.amount : 0, tank.getCapacity()));
    }

    private void drawFluid(FluidStack fluidStack, int scaledAmount) {
        if (fluidStack == null) {
            return;
        }

        Fluid fluid = fluidStack.getFluid();

        if (fluid == null) {
            return;
        }

        int x = gui.getGuiLeft() + bounds.getX() + 1;
        int y = gui.getGuiTop() + bounds.getY() + 1;
        int height = bounds.getHeight() - 2;
        int width = bounds.getWidth() - 2;

        int TEX_WIDTH = 16;
        int TEX_HEIGHT = 16;

        TextureMap textureMapBlocks = mc.getTextureMapBlocks();
        ResourceLocation fluidStill = fluid.getStill();
        TextureAtlasSprite fluidStillSprite = null;

        if (fluidStill != null) {
            fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
        }

        if (fluidStillSprite == null) {
            fluidStillSprite = textureMapBlocks.getMissingSprite();
        }

        int fluidColor = fluid.getColor(fluidStack);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        setGLColorFromInt(fluidColor);

        int xTileCount = width / TEX_WIDTH;
        int xRemainder = width - (xTileCount * TEX_WIDTH);
        int yTileCount = scaledAmount / TEX_HEIGHT;
        int yRemainder = scaledAmount - (yTileCount * TEX_HEIGHT);

        int yStart = y + height;

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            for (int yTile = 0; yTile <= yTileCount; yTile++) {
                int width_ = (xTile == xTileCount) ? xRemainder : TEX_WIDTH;
                int height_ = (yTile == yTileCount) ? yRemainder : TEX_HEIGHT;
                int x_ = x + (xTile * TEX_WIDTH);
                int y_ = yStart - ((yTile + 1) * TEX_HEIGHT);

                if (width_ > 0 && height_ > 0) {
                    int maskTop = TEX_HEIGHT - height_;
                    int maskRight = TEX_WIDTH - width_;

                    drawFluidTexture(x_, y_, fluidStillSprite, maskTop, maskRight, 100);
                }
            }
        }
    }
}

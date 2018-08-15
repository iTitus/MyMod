package io.github.ititus.mymod.client.render.tile;

import io.github.ititus.mymod.tile.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TESRTank extends FastTESR<TileTank> {

    private static int getColor(FluidStack fluidStack) {
        if (fluidStack != null && fluidStack.amount > 0) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid != null) {
                return fluid.getColor(fluidStack);
            }
        }
        return 0xFFFFFFFF;
    }

    private static void addVertexWithUV(BufferBuilder wr, double x, double y, double z, double u, double v, int color, int skyLight, int blockLight) {
        wr.pos(x, y, z).color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF).tex(u, v).lightmap(skyLight, blockLight).endVertex();
    }

    private static TextureAtlasSprite getFluidTexture(FluidStack fluidStack) {
        if (fluidStack != null) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid != null) {
                return getTextureAtlasLocation(fluid.getStill(fluidStack));
            }
        }
        return null;
    }

    private static TextureAtlasSprite getTextureAtlasLocation(ResourceLocation textureLocation) {
        return textureLocation != null ? Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(textureLocation.toString()) : null;
    }

    @Override
    public void renderTileEntityFast(TileTank tank, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        TextureAtlasSprite texture = getFluidTexture(tank.tank.getFluid());
        if (texture == null) {
            return;
        }

        double fillRaw = getFillRatio(tank.tank.getFluid(), tank.tank.getCapacity());
        if (fillRaw <= 0) {
            return;
        }
        double eps = 1D / 1024;
        double fill = Math.max(eps, Math.min(1 - eps, fillRaw));

        int light = tank.getBlockState().getPackedLightmapCoords(tank.getWorld(), tank.getPos());
        int skyLight = light >> 16 & 0xFFFF; //0x00F0;
        int blockLight = light & 0xFFFF; //0x00F0;
        int color = getColor(tank.tank.getFluid());

        double uMin = texture.getMinU();
        double uMax = texture.getMaxU();
        double vMin = texture.getMinV();
        double vMax = texture.getMaxV();
        double vHeight = vMax - vMin;

        buffer.setTranslation(x, y, z);
        addVertexWithUV(buffer, 1 - eps, eps, eps, uMax, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, fill, eps, uMax, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, eps, uMin, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, eps, eps, uMin, vMin, color, skyLight, blockLight);

        addVertexWithUV(buffer, 1 - eps, eps, 1 - eps, uMin, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, fill, 1 - eps, uMin, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, 1 - eps, uMax, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, eps, 1 - eps, uMax, vMin, color, skyLight, blockLight);

        addVertexWithUV(buffer, 1 - eps, eps, eps, uMin, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, fill, eps, uMin, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, fill, 1 - eps, uMax, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, eps, 1 - eps, uMax, vMin, color, skyLight, blockLight);

        addVertexWithUV(buffer, eps, eps, 1 - eps, uMin, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, 1 - eps, uMin, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, eps, uMax, vMin + (vHeight * fillRaw), color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, eps, eps, uMax, vMin, color, skyLight, blockLight);

        addVertexWithUV(buffer, 1 - eps, eps, eps, uMax, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, eps, 1 - eps, uMin, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, eps, 1 - eps, uMin, vMax, color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, eps, eps, uMax, vMax, color, skyLight, blockLight);

        addVertexWithUV(buffer, 1 - eps, fill, eps, uMax, vMin, color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, eps, uMax, vMax, color, skyLight, blockLight);
        addVertexWithUV(buffer, eps, fill, 1 - eps, uMin, vMax, color, skyLight, blockLight);
        addVertexWithUV(buffer, 1 - eps, fill, 1 - eps, uMin, vMin, color, skyLight, blockLight);
        buffer.setTranslation(0, 0, 0);
    }

    private double getFillRatio(FluidStack fluidStack, double capacity) {
        if (fluidStack != null && fluidStack.amount > 0) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid != null) {
                return Math.max(0, Math.min(1, fluidStack.amount / capacity));
            }
        }
        return 0;
    }
}

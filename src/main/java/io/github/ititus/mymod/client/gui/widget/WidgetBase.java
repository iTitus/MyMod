package io.github.ititus.mymod.client.gui.widget;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.client.gui.GuiBase;
import io.github.ititus.mymod.inventory.container.ContainerBase;
import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;

import java.util.List;

public class WidgetBase extends Gui {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(MyMod.MOD_ID, "textures/gui/widgets.png");
    protected static final int SHEET_WIDTH = 256, SHEET_HEIGHT = 256;
    protected final Rectangle bounds;
    protected final int u, v, texWidth, texHeight;
    protected final ResourceLocation texture;
    protected final Minecraft mc;
    protected int id;
    protected GuiBase<? extends TileBase, ? extends ContainerBase<? extends TileBase>> gui;

    public WidgetBase(int x, int y, int w, int h) {
        this(x, y, w, h, 0, 0, 0, 0, null);
    }

    public WidgetBase(int x, int y, int w, int h, int u, int v, int texWidth, int texHeight) {
        this(x, y, w, h, u, v, texWidth, texHeight, TEXTURE);
    }

    public WidgetBase(int x, int y, int w, int h, int u, int v, int texWidth, int texHeight, ResourceLocation texture) {
        this.id = -1;
        this.bounds = new Rectangle(x, y, w, h);
        this.gui = null;
        this.u = u;
        this.v = v;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.texture = texture;
        this.mc = Minecraft.getMinecraft();
        this.zLevel = 0;
    }

    public void setGui(GuiBase<? extends TileBase, ? extends ContainerBase<? extends TileBase>> gui) {
        if (this.gui != null) {
            throw new IllegalStateException();
        }
        this.gui = gui;
        this.zLevel = gui.getZLevel();
    }

    public void drawForeground(int mouseX, int mouseY) {
    }

    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        if (texture == null) {
            return;
        }
        bindTexture(texture);
        drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), bounds.getWidth(), bounds.getHeight(), u, v, texWidth, texHeight, SHEET_WIDTH, SHEET_HEIGHT);
    }

    public void onClick(int mouseButton) {
    }

    public void addInformation(List<String> tooltip, boolean advanced) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id > 0) {
            throw new IllegalStateException();
        }
        this.id = id;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    protected void bindTexture(ResourceLocation texture) {
        mc.getTextureManager().bindTexture(texture);
    }

    public float getZLevel() {
        return zLevel;
    }

    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
    }

    protected void drawTexturedModalRect(int x, int y, int width, int height, int uMin, int vMin, int uMax, int vMax, int sheetWidth, int sheetHeight) {
        double texScaleX = 1D / sheetWidth;
        double texScaleY = 1D / sheetHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, zLevel).tex(uMin * texScaleX, (vMin + vMax) * texScaleY).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex((uMin + uMax) * texScaleX, (vMin + vMax) * texScaleY).endVertex();
        buffer.pos(x + width, y, zLevel).tex((uMin + uMax) * texScaleX, vMin * texScaleY).endVertex();
        buffer.pos(x, y, zLevel).tex(uMin * texScaleX, vMin * texScaleY).endVertex();
        tessellator.draw();
    }
}

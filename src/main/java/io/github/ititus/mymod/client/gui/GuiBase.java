package io.github.ititus.mymod.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.client.gui.widget.WidgetBase;
import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerBase;
import io.github.ititus.mymod.network.NetworkHandler;
import io.github.ititus.mymod.network.message.MessageGuiUpdate;
import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.network.NetworkState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBase<T extends TileBase, C extends ContainerBase<T>> extends GuiContainer {

    protected static final ResourceLocation TEXTURE_BLANK = new ResourceLocation(MyMod.MOD_ID, "textures/gui/container/blank.png");

    protected final Minecraft mc;
    protected final C container;
    protected final ResourceLocation texture;
    protected final int xSize, ySize;
    protected final List<WidgetBase> widgets = Lists.newArrayList();
    private boolean showPlayerInv;
    private int nextId;

    public GuiBase(C container, int xSize, int ySize, ResourceLocation texture) {
        super(container);
        this.mc = Minecraft.getMinecraft();
        this.container = container;
        this.texture = texture;
        this.xSize = xSize;
        this.ySize = ySize;
        this.showPlayerInv = false;
        this.nextId = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        widgets.clear();
        nextId = 0;
        showPlayerInv = false;
    }

    protected int addWidget(WidgetBase widget) {
        if (widget != null) {
            widget.setGui(this);
            widget.setId(nextId++);
            if (widgets.add(widget)) {
                return widget.getId();
            }
        }
        return -1;
    }

    public WidgetBase getWidget(int id) {
        return getWidget(id, WidgetBase.class);
    }

    public <T extends WidgetBase> T getWidget(int id, Class<T> clazz) {
        return (T) widgets.stream().filter(widget -> widget.getId() == id).findFirst().orElse(null);
    }

    protected void bindTexture(ResourceLocation texture) {
        mc.getTextureManager().bindTexture(texture);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = getGuiTitle();
        if (!Strings.isNullOrEmpty(s)) {
            fontRenderer.drawString(s, (xSize - fontRenderer.getStringWidth(s)) / 2, 6, 0x404040);
        }
        if (showPlayerInv) {
            fontRenderer.drawString(getPlayerInventory().getDisplayName().getUnformattedText(), 8, ySize - 93, 0x404040);
        }

        widgets.forEach(widget -> {
            GlStateManager.color(1f, 1f, 1f);
            widget.drawForeground(mouseX, mouseY);
        });
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        widgets.forEach(widget -> {
            GlStateManager.color(1f, 1f, 1f);
            widget.drawBackground(partialTicks, mouseX, mouseY);
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        boolean advanced = mc.gameSettings.advancedItemTooltips;
        List<String> tooltip = Lists.newArrayList();
        widgets.stream().filter(widget -> widget.getBounds().contains(mouseX - guiLeft, mouseY - guiTop)).sorted(Comparator.comparingDouble(WidgetBase::getZLevel).reversed()).forEachOrdered(widget -> widget.addInformation(tooltip, advanced));
        drawHoveringText(tooltip, mouseX, mouseY);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        widgets.stream().filter(widget -> widget.getBounds().contains(mouseX - guiLeft, mouseY - guiTop)).sorted(Comparator.comparingDouble(WidgetBase::getZLevel).reversed()).forEachOrdered(widget -> widget.onClick(mouseButton));
    }

    public float getZLevel() {
        return zLevel;
    }

    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public SoundHandler getSoundHandler() {
        return mc.getSoundHandler();
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public T getTile() {
        return container.getTile();
    }

    public C getContainer() {
        return container;
    }

    public InventoryPlayer getPlayerInventory() {
        return container.getPlayerInventory();
    }

    public RenderItem getRenderItem() {
        return itemRender;
    }

    public EntityPlayer getPlayer() {
        return container.getPlayer();
    }

    public String getGuiTitle() {
        T tile = getTile();
        if (tile != null) {
            return tile.getDisplayName().getUnformattedText();
        }
        return null;
    }

    protected void addPlayerInventory(int x, int y) {
        InventoryPlayer inv = getPlayerInventory();
        if (inv != null) {
            showPlayerInv = true;
            for (int i = 0; i < 9; i++) {
                addWidget(new WidgetSlot(8 + i * 18 - 1, y + 58 - 1));
            }
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    addWidget(new WidgetSlot(x + col * 18 - 1, y + row * 18 - 1));
                }
            }
        }
    }

    protected void sendTileToServer() {
        TileBase tile = getTile();
        NetworkState state = tile.getGuiState();
        if (state != null) {
            tile.updateGuiState(state);
            NetworkHandler.INSTANCE.sendToServer(new MessageGuiUpdate(tile.getPos(), state));
        }
    }
}
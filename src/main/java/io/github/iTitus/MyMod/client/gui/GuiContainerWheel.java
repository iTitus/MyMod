package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.inventory.container.ContainerWheel;
import io.github.iTitus.MyMod.lib.LibGUI;
import io.github.iTitus.MyMod.lib.LibTextures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiContainerWheel extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			LibTextures.TEXTURE_LOCATION, LibGUI.getTextureLoc("wheel"));
	private ContainerWheel container;

	public GuiContainerWheel(ContainerWheel container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(((width - xSize) / 2),
				((height - ySize) / 2), 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(
				StatCollector.translateToLocal("gui.wheel.name"), 8, 6,
				0x404040);
		fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 0x404040);

	}

}

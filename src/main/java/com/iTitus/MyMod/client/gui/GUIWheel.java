package com.iTitus.MyMod.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.iTitus.MyMod.inventory.container.ContainerWheel;
import com.iTitus.MyMod.lib.LibGUI;
import com.iTitus.MyMod.lib.LibTextures;

public class GUIWheel extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			LibTextures.TEXTURE_LOCATION, LibGUI.getTextureLoc("wheel"));
	private ContainerWheel container;

	public GUIWheel(ContainerWheel container) {
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
		fontRendererObj
				.drawString(StatCollector.translateToLocal("gui.wheel.name"),
						8, 6, 4210752);
		fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 4210752);

	}

}

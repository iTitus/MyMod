package io.github.iTitus.MyMod.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIClockConfig extends GuiScreen {

	public GUIClockConfig() {

	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.name"),
				width / 2, 40, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

	}

	@Override
	protected void actionPerformed(GuiButton button) {

		switch (button.id) {
		case 0:

			break;

		default:
			break;
		}

	}

}

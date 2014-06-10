package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.handler.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIClockConfig extends GuiScreen {

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
		int id = 0;
		buttonList.add(new GuiSwitchButton(id, 100, 100 + 50 * id,
				"Clock format", 2, new String[] { "None", "Analog", "Digital",
						"Both" }));
	}

	@Override
	protected void actionPerformed(GuiButton button) {

		switch (button.id) {
		case 0:
			ConfigHandler.analog_digital = ((GuiSwitchButton) button)
					.getCurrentIndex();
			ConfigHandler.save();
			break;

		default:
			break;
		}

	}

}

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
		buttonList.add(new GuiSwitchButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Clock format", 2, new String[] {
				"None", "Analog", "Digital", "Both" }));
		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Seconds", true));
		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "AM/PM", false));

	}

	@Override
	protected void actionPerformed(GuiButton button) {

		switch (button.id) {
		case 0:
			ConfigHandler.analog_digital = ((GuiSwitchButton) button)
					.getCurrentIndex();
			ConfigHandler.save();
			break;
		case 1:
			ConfigHandler.seconds = ((GuiOnOffButton) button).getCurrentValue();
			ConfigHandler.save();
			break;
		case 2:
			ConfigHandler.am_pm = ((GuiOnOffButton) button).getCurrentValue();
			ConfigHandler.save();
			break;
		default:
			break;
		}

	}

}

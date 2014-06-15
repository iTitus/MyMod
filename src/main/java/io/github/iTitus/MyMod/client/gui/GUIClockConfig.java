package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.handler.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIClockConfig extends GuiScreen {

	private GuiTextField separator;

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.name"),
				width / 2, 40, 16777215);
		drawCenteredString(this.fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.separator"),
				(width / 2) - 50, (height / 4) + 88, 16777215);
		separator.drawTextBox();
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {
		int id = 0;
		buttonList
				.add(new GuiSwitchButton(
						id,
						(width / 2) - 100,
						(height / 4) + (24 * (id + 1)) - 16,
						StatCollector
								.translateToLocal("gui.clockConfig.clockFormat"),
						ConfigHandler.analog_digital,
						new String[] {
								StatCollector
										.translateToLocal("gui.clockConfig.clockFormat.none"),
								StatCollector
										.translateToLocal("gui.clockConfig.clockFormat.analog"),
								StatCollector
										.translateToLocal("gui.clockConfig.clockFormat.digital"),
								StatCollector
										.translateToLocal("gui.clockConfig.clockFormat.both") }));
		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, StatCollector
				.translateToLocal("gui.clockConfig.seconds"),
				ConfigHandler.seconds));
		id++;
		buttonList
				.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
						+ (24 * (id + 1)) - 16, StatCollector
						.translateToLocal("gui.clockConfig.am/pm"),
						ConfigHandler.am_pm));
		id++;
		Keyboard.enableRepeatEvents(true);
		separator = new GuiTextField(fontRendererObj, (width / 2), (height / 4)
				+ (24 * (id + 1)) - 16, 100, 20);
		separator.setText(ConfigHandler.separator);

		id++;
		buttonList.add(new GuiButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, StatCollector
				.translateToLocal("gui.alarmConfig.name")));

		id++;
		buttonList
				.add(new GuiButton(id, (width / 2) - 100, (height / 4)
						+ (24 * (id + 2)) - 16, I18n.format("gui.done",
						new Object[0])));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
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
		case 4:
			mc.displayGuiScreen(new GUIAlarmConfig(this));
			break;
		default:
			mc.setIngameFocus();
		}

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (separator.textboxKeyTyped(par1, par2)) {
			ConfigHandler.separator = separator.getText();
			ConfigHandler.save();
		} else {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		separator.mouseClicked(par1, par2, par3);
	}

}

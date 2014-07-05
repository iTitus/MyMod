package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.util.RenderUtil;
import io.github.iTitus.MyMod.common.handler.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenClockConfig extends GuiScreen {

	private GuiIntSliderButton rColor, gColor, bColor;
	private GuiTextField separator;

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.name"),
				width / 2, 16, 16777215);
		drawCenteredString(this.fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.separator"),
				(width / 2) - 40, (height / 8) + 110, 16777215);
		separator.drawTextBox();
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {
		int id = 0;
		buttonList
				.add(new GuiSwitchButton(
						id,
						(width / 2) - 210,
						(height / 8) + (24 * (id + 2)) - 16,
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
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
				+ (24 * (id + 2)) - 16, StatCollector
				.translateToLocal("gui.clockConfig.seconds"),
				ConfigHandler.seconds));
		id++;
		buttonList
				.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
						+ (24 * (id + 2)) - 16, StatCollector
						.translateToLocal("gui.clockConfig.am/pm"),
						ConfigHandler.am_pm));

		id++;
		rColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 1)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.red"),
				RenderUtil.getColor(ConfigHandler.color)[0], 0, 255);
		buttonList.add(rColor);

		id++;
		gColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 1)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.green"),
				RenderUtil.getColor(ConfigHandler.color)[1], 0, 255);
		buttonList.add(gColor);

		id++;
		bColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 1)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.blue"),
				RenderUtil.getColor(ConfigHandler.color)[2], 0, 255);
		buttonList.add(bColor);

		id++;
		Keyboard.enableRepeatEvents(true);
		separator = new GuiTextField(fontRendererObj, (width / 2), (height / 8)
				+ (24 * (id - 1)) - 16, 100, 20);
		separator.setText(ConfigHandler.separator);

		id++;
		buttonList.add(new GuiButton(id, (width / 2) - 100, (height / 8)
				+ (24 * id) - 16, StatCollector
				.translateToLocal("gui.alarmConfig.name")));

		id++;
		buttonList.add(new GuiButton(id, (width / 2) - 100, (height / 8)
				+ (24 * id) - 16, I18n.format("gui.done", new Object[0])));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		if (rColor.isSliding() || gColor.isSliding() || bColor.isSliding()) {
			ConfigHandler.color = RenderUtil.getColor(
					rColor.getSliderPosition(), gColor.getSliderPosition(),
					bColor.getSliderPosition());
			ConfigHandler.saveConfig();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {

		switch (button.id) {
		case 0:
			ConfigHandler.analog_digital = ((GuiSwitchButton) button)
					.getCurrentIndex();
			ConfigHandler.saveConfig();
			break;
		case 1:
			ConfigHandler.seconds = ((GuiOnOffButton) button).getCurrentValue();
			ConfigHandler.saveConfig();
			break;
		case 2:
			ConfigHandler.am_pm = ((GuiOnOffButton) button).getCurrentValue();
			ConfigHandler.saveConfig();
			break;
		case 7:
			mc.displayGuiScreen(new GuiScreenAlarmConfig(this));
			break;
		case 8:
			mc.setIngameFocus();
		default:
		}

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (separator.textboxKeyTyped(par1, par2)) {
			ConfigHandler.separator = separator.getText();
			ConfigHandler.saveConfig();
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

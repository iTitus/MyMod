package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIEditAlarm extends GuiScreen {

	private Alarm alarm;
	private boolean isNewAlarm;
	private GUIAlarmConfig parent;

	private GuiTextField title;
	private GuiButton doneButton, enabledButton, repeatButton;

	public GUIEditAlarm(GUIAlarmConfig parent, Alarm alarm, boolean isNewAlarm) {
		this.parent = parent;
		this.alarm = alarm;
		this.isNewAlarm = isNewAlarm;
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, (isNewAlarm ? "New Alarm"
				: "Edit alarm"), this.width / 2, 20, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

		int id = 1;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Enabled", (isNewAlarm ? true : alarm
				.isEnabled())));

		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Repeat", (isNewAlarm ? false : alarm
				.isEnabled())));

		id++;
		doneButton = new GuiButton(id, (id * ((200 + (width - 400) / 3)))
				+ ((width - 400) / 3), height - 32, (isNewAlarm ? "Add alarm"
				: "Save changes"));
		doneButton.enabled = false;
		buttonList.add(doneButton);

		id++;
		buttonList.add(new GuiButton(id, (id * ((200 + (width - 400) / 3)))
				+ ((width - 400) / 3), height - 32, I18n.format("gui.cancel",
				new Object[0])));

	}

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button.enabled) {
			switch (button.id) {
			case 0:
			case 1:
				break;
			case 2:
				saveAlarm();
				break;
			default:
				mc.displayGuiScreen(parent);
			}

		}

	}

	private void saveAlarm() {

		if (isNewAlarm) {

		} else {

		}

	}
}

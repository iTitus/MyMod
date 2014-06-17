package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import io.github.iTitus.MyMod.client.handler.AlarmHandler;
import io.github.iTitus.MyMod.handler.ConfigHandler;
import io.github.iTitus.MyMod.util.TimeUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIEditAlarm extends GuiScreen {

	private Alarm alarm;
	private int index;
	private boolean isNewAlarm;

	private GUIAlarmConfig parent;
	private GuiTextField title;
	GuiOnOffButton enabledButton, repeatButton;
	GuiSwitchButton hourButton, minButton;

	public GUIEditAlarm(GUIAlarmConfig parent, int index, Alarm alarm,
			boolean isNewAlarm) {
		this.parent = parent;
		this.index = index;
		this.alarm = alarm;
		this.isNewAlarm = isNewAlarm;
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		title.drawTextBox();
		drawCenteredString(
				fontRendererObj,
				(isNewAlarm ? StatCollector
						.translateToLocal("gui.editAlarm.new.name")
						: StatCollector.translateToLocal("gui.editAlarm.name")),
				width / 2, 20, 16777215);
		fontRendererObj.drawString(
				StatCollector.translateToLocal("gui.editAlarm.alarmTitle"),
				(width / 2) - 100, (height / 4) + 8
						- fontRendererObj.FONT_HEIGHT - 1, 16777215);
		drawCenteredString(fontRendererObj, ConfigHandler.separator, width / 2,
				(height / 4) + 37, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

		Keyboard.enableRepeatEvents(true);
		title = new GuiTextField(fontRendererObj, (width / 2) - 100,
				(height / 4) + 8, 200, 20);
		if (!isNewAlarm)
			title.setText(alarm.getTitle());

		int id = 2;
		hourButton = new GuiSwitchButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, 75, 20, null, (isNewAlarm ? 0
				: alarm.getHour()), TimeUtil.getAllHours());
		buttonList.add(hourButton);

		id++;
		minButton = new GuiSwitchButton(id, (width / 2) + 25, (height / 4)
				+ (24 * id) - 16, 75, 20, null, (isNewAlarm ? 0
				: alarm.getMin()), TimeUtil.getAllMins());
		buttonList.add(minButton);
		id--;

		id++;
		enabledButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16,
				StatCollector.translateToLocal("gui.editAlarm.enabled"),
				(isNewAlarm ? true : alarm.isEnabled()));
		buttonList.add(enabledButton);

		id++;
		repeatButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16,
				StatCollector.translateToLocal("gui.editAlarm.repeat"),
				(isNewAlarm ? false : alarm.isRepeating()));
		buttonList.add(repeatButton);

		id++;
		buttonList
				.add(new GuiButton(
						id,
						((id - 5) * ((200 + (width - 400) / 3)))
								+ ((width - 400) / 3),
						height - 32,
						(isNewAlarm ? StatCollector
								.translateToLocal("gui.editAlarm.new.done")
								: StatCollector
										.translateToLocal("gui.editAlarm.done"))));

		id++;
		buttonList.add(new GuiButton(id,
				((id - 5) * ((200 + (width - 400) / 3))) + ((width - 400) / 3),
				height - 32, I18n.format("gui.cancel", new Object[0])));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	private void saveAlarm() {

		alarm = new Alarm(title.getText(), hourButton.getCurrentIndex(),
				minButton.getCurrentIndex(), repeatButton.getCurrentValue());
		alarm.setEnabled(enabledButton.getCurrentValue());

		if (isNewAlarm) {
			AlarmHandler.add(alarm);
		} else {
			AlarmHandler.editAlarm(index, alarm);
		}

		parent.getGuiAlarmList().setAlarms(AlarmHandler.alarms);

	}

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button.enabled) {
			switch (button.id) {
			case 5:
				saveAlarm();
			case 6:
				mc.displayGuiScreen(parent);
			default:

			}

		}

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (title.textboxKeyTyped(par1, par2)) {
		} else
			super.keyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		title.mouseClicked(x, y, button);
		if (button == 1) {
			hourButton.mouseRightPressed(mc, x, y);
			minButton.mouseRightPressed(mc, x, y);
		}
	}
}

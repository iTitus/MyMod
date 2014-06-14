package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import io.github.iTitus.MyMod.handler.AlarmHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIEditAlarm extends GuiScreen {

	private Alarm alarm;
	private GuiButton doneButton;
	private int index;
	private boolean isNewAlarm;

	private GUIAlarmConfig parent;
	private GuiTextField title;
	GuiOnOffButton enabledButton, repeatButton;

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
		drawCenteredString(fontRendererObj, (isNewAlarm ? "New Alarm"
				: "Edit alarm"), this.width / 2, 20, 16777215);
		fontRendererObj.drawString("Title", (width / 2) - 100, (height / 4) + 8
				- fontRendererObj.FONT_HEIGHT - 1, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

		Keyboard.enableRepeatEvents(true);
		title = new GuiTextField(fontRendererObj, (width / 2) - 100,
				(height / 4) + 8, 200, 20);
		if (!isNewAlarm)
			title.setText(alarm.getTitle());

		// TODO: special fields for input of hour and min

		int id = 1;
		enabledButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Enabled", (isNewAlarm ? true
				: alarm.isEnabled()));
		buttonList.add(enabledButton);

		id++;
		repeatButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, "Repeat", (isNewAlarm ? false
				: alarm.isRepeating()));
		buttonList.add(repeatButton);

		id++;
		doneButton = new GuiButton(id, ((id - 3) * ((200 + (width - 400) / 3)))
				+ ((width - 400) / 3), height - 32, (isNewAlarm ? "Add alarm"
				: "Save changes"));
		doneButton.enabled = !isNewAlarm;
		buttonList.add(doneButton);

		id++;
		buttonList.add(new GuiButton(id,
				((id - 3) * ((200 + (width - 400) / 3))) + ((width - 400) / 3),
				height - 32, I18n.format("gui.cancel", new Object[0])));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		if (title.getText() != null && title.getText() != "")
			doneButton.enabled = true;
		else
			doneButton.enabled = false;
	}

	private void saveAlarm() {

		// TODO: Dynamic input of hour and min
		alarm = new Alarm(title.getText(), 0, 0, repeatButton.getCurrentValue());
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
			case 1:
			case 2:
				break;
			case 3:
				saveAlarm();
			default:
				mc.displayGuiScreen(parent);
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
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		title.mouseClicked(par1, par2, par3);
	}
}

package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.handler.AlarmHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAlarmConfig extends GuiScreen {

	private GuiAlarmList alarmList;
	private GuiButton editButton, deleteButton;

	private GUIClockConfig parent;

	public GUIAlarmConfig(GUIClockConfig parent) {
		this.parent = parent;
	}

	@Override
	public void confirmClicked(boolean accepted, int index) {

		if (accepted) {
			AlarmHandler.remove(index);
			alarmList.select(-1);
			alarmList.setAlarms(AlarmHandler.alarms);
		}

		mc.displayGuiScreen(this);

	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		alarmList.drawScreen(x, y, partialTicks);
		drawCenteredString(fontRendererObj, "Alarm configuration",
				this.width / 2, 20, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

		alarmList = new GuiAlarmList(this, mc, width, height, 48, height - 48,
				25);
		alarmList.setAlarms(AlarmHandler.alarms);

		int id = 0;
		editButton = new GuiButton(id, 10, height - 32, 100, 20, "Edit");
		editButton.enabled = false;
		buttonList.add(editButton);

		id++;
		buttonList.add(new GuiButton(id, (width / 3) - 50, height - 32, 100,
				20, "Add alarm"));

		id++;
		deleteButton = new GuiButton(id, ((2 * width) / 3) - 50, height - 32,
				100, 20, "Delete");
		deleteButton.enabled = false;
		buttonList.add(deleteButton);

		id++;
		buttonList.add(new GuiButton(id, width - 110, height - 32, 100, 20,
				I18n.format("gui.done", new Object[0])));

	}

	public void select(int index) {
		alarmList.select(index);
		if (index >= 0) {
			editButton.enabled = true;
			deleteButton.enabled = true;
		}

	}

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button.enabled) {
			switch (button.id) {
			case 0:
			case 1:
				GuiAlarm guiAlarm = (GuiAlarm) alarmList.getListEntry(alarmList
						.getSelected());
				mc.displayGuiScreen(new GUIEditAlarm(this,
						(guiAlarm != null) ? (guiAlarm.getAlarm()) : null));
				break;
			case 2:
				mc.displayGuiScreen(new GuiYesNo(
						this,
						"Do you really want to delete this alarm?",
						"'"
								+ ((GuiAlarm) alarmList.getListEntry(alarmList
										.getSelected())).getAlarm().getTitle()
								+ "'" + " will be lost forever! (A long time!)",
						alarmList.getSelected()));
				break;
			default:
				mc.displayGuiScreen(parent);
			}
		}

	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		alarmList.func_148179_a(x, y, mouseButton);
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int type) {
		super.mouseMovedOrUp(x, y, type);
		alarmList.func_148181_b(x, y, type);
	}

}

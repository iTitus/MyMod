package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAlarmConfig extends GuiScreen {

	private GuiAlarmList alarmList;
	private ArrayList<Alarm> alarms;

	private GuiButton editButton, deleteButton;

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

		alarms = loadAlarms();
		alarmList = new GuiAlarmList(this, mc, width, height, 48, height - 48,
				25);
		alarmList.addAlarms(alarms);

		int id = 0;
		editButton = new GuiButton(id, 10, height - 32, 50, 20, "Edit");
		editButton.enabled = false;
		buttonList.add(editButton);

	}

	public void select(int index) {
		alarmList.select(index);
		IGuiListEntry entry = index < 0 ? null : alarmList.getListEntry(index);
		if (entry != null) {

		}

	}

	private ArrayList<Alarm> loadAlarms() {
		ArrayList<Alarm> list = new ArrayList<Alarm>();

		list.add(new Alarm("Testing AM/PM", 0, 0, true));
		list.add(new Alarm("Testing AM/PM 2 ", 12, 00, true));

		for (int i = 1; i < 43; i++) {
			list.add(new Alarm("Test Alarm " + i, i % 12, 60 % i, i % 2 == 1));
		}

		return list;
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

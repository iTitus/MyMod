package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarmList extends GuiListExtended {

	private ArrayList<GuiAlarm> alarmList;
	private int indexSelected;
	private final GUIAlarmConfig parent;

	public GuiAlarmList(GUIAlarmConfig parent, Minecraft mc, int width,
			int height, int top, int bottom, int slotHeight) {
		super(mc, width, height, top, bottom, slotHeight);
		this.parent = parent;
		alarmList = new ArrayList<GuiAlarm>();
		indexSelected = -1;
	}

	public ArrayList<Alarm> getAlarms() {

		ArrayList<Alarm> alarms = new ArrayList<Alarm>();

		for (GuiAlarm alarm : alarmList) {
			alarms.add(alarm.getAlarm());
		}

		return alarms;
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		if (index < 0 || index > alarmList.size())
			return null;
		return alarmList.get(index);
	}

	@Override
	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	public int getSelected() {
		return indexSelected;
	}

	@Override
	public boolean isSelected(int index) {
		return indexSelected == index;
	}

	public void select(int index) {
		indexSelected = index;
	}

	public void setAlarms(ArrayList<Alarm> arrayList) {

		alarmList.clear();

		for (Alarm alarm : arrayList) {
			alarmList.add(new GuiAlarm(alarm, parent));
		}

	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 16;
	}

	@Override
	protected int getSize() {
		return alarmList.size();
	}

}

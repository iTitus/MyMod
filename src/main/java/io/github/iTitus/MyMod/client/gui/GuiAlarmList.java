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
	private final GUIAlarmConfig parent;

	public GuiAlarmList(GUIAlarmConfig parent, Minecraft mc, int width,
			int height, int top, int bottom, int slotHeight) {
		super(mc, width, height, top, bottom, slotHeight);
		this.parent = parent;
		alarmList = new ArrayList<GuiAlarm>();
	}

	public void addAlarms(ArrayList<Alarm> arrayList) {

		for (Alarm alarm : arrayList) {
			alarmList.add(new GuiAlarm(alarm));
		}

	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return alarmList.get(index);
	}

	@Override
	protected int getSize() {
		return alarmList.size();
	}

}

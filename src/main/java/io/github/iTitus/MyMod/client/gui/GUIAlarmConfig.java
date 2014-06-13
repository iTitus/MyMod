package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAlarmConfig extends GuiScreen {

	private GuiAlarmList alarmList;
	private ArrayList<Alarm> alarms;

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

		alarmList = new GuiAlarmList(this, mc, width, height, 10, height - 64,
				25);
		alarmList.addAlarms(alarms);

	}

	private ArrayList<Alarm> loadAlarms() {
		return new ArrayList<Alarm>();
	}

}

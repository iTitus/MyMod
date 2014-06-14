package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIEditAlarm extends GuiScreen {

	private Alarm alarm;
	private GUIAlarmConfig parent;

	public GUIEditAlarm(GUIAlarmConfig parent, Alarm alarm) {
		this.parent = parent;
		this.alarm = alarm;

	}

}

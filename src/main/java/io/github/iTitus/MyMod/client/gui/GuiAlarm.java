package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.helper.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarm implements IGuiListEntry {

	public static class Alarm {

		private boolean enabled, repeat;
		private int hour, min;
		private String title;

		public Alarm(String title, int hour, int min, boolean repeat) {
			this.title = title;
			this.hour = hour;
			this.min = min;
			enabled = true;
			this.repeat = repeat;
		}

		public int getHour() {
			return hour;
		}

		public int getMin() {
			return min;
		}

		public String getTitle() {
			return title;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public boolean isRepeating() {
			return repeat;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

	}

	private Alarm alarm;
	private GUIAlarmConfig parent;
	private long timeLastHit;

	public GuiAlarm(Alarm alarm, GUIAlarmConfig parent) {
		this.alarm = alarm;
		this.parent = parent;
		timeLastHit = 0L;
	}

	@Override
	public void drawEntry(int var1, int var2, int var3, int width, int var5,
			Tessellator tess, int var7, int var8, boolean var9) {
		Minecraft.getMinecraft().fontRenderer
				.drawString(alarm.getTitle(), var2 + 32, var3 + 1,
						(alarm.isEnabled() ? (65280) : (16711680)));
		Minecraft.getMinecraft().fontRenderer.drawString(
				TimeHelper.getTimeString(alarm.getHour(), alarm.getMin())
						+ ((alarm.isRepeating()) ? (" - Repeats every day")
								: ("")), var2 + 32,
				var3 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2,
				(alarm.isEnabled() ? (65280) : (16711680)));
	}

	@Override
	public boolean mousePressed(int index, int var2, int var3, int var4,
			int var5, int var6) {

		parent.select(index);

		if (Minecraft.getSystemTime() - timeLastHit < 250L) {
			alarm.setEnabled(!alarm.isEnabled());
		}

		timeLastHit = Minecraft.getSystemTime();
		return false;
	}

	@Override
	public void mouseReleased(int var1, int var2, int var3, int var4, int var5,
			int var6) {
	}

}

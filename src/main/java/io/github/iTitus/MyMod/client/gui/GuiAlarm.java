package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.client.handler.AlarmHandler;
import io.github.iTitus.MyMod.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarm implements IGuiListEntry {

	public static class Alarm {

		private static final String TAG_TITLE = "Title", TAG_HOUR = "Hour",
				TAG_MIN = "Min", TAG_REPEAT = "Repeats",
				TAG_ENABLED = "Enabled";

		public static Alarm readFromNBT(NBTTagCompound nbt) {
			return new Alarm(nbt.getString(TAG_TITLE),
					nbt.getInteger(TAG_HOUR), nbt.getInteger(TAG_MIN),
					nbt.getBoolean(TAG_REPEAT)).setEnabled(nbt
					.getBoolean(TAG_ENABLED));
		}

		public static NBTTagCompound writeToNBT(Alarm alarm) {

			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setString(TAG_TITLE, alarm.title);
			nbt.setInteger(TAG_HOUR, alarm.hour);
			nbt.setInteger(TAG_MIN, alarm.min);
			nbt.setBoolean(TAG_REPEAT, alarm.repeat);
			nbt.setBoolean(TAG_ENABLED, alarm.enabled);

			return nbt;
		}

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

		public Alarm setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

	}

	private Alarm alarm;
	private GuiScreenAlarmConfig parent;
	private long timeLastHit;

	public GuiAlarm(Alarm alarm, GuiScreenAlarmConfig parent) {
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
				TimeUtil.getTimeString(alarm.getHour(), alarm.getMin())
						+ ((alarm.isRepeating()) ? (" - " + StatCollector
								.translateToLocal("gui.alarmConfig.repeat"))
								: ("")), var2 + 32,
				var3 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2,
				(alarm.isEnabled() ? (65280) : (16711680)));
	}

	public Alarm getAlarm() {
		return alarm;
	}

	@Override
	public boolean mousePressed(int index, int var2, int var3, int var4,
			int var5, int var6) {

		parent.select(index);

		if (Minecraft.getSystemTime() - timeLastHit < 250L) {
			AlarmHandler.editAlarm(index, alarm.setEnabled(!alarm.isEnabled()));
		}

		timeLastHit = Minecraft.getSystemTime();
		return false;
	}

	@Override
	public void mouseReleased(int var1, int var2, int var3, int var4, int var5,
			int var6) {
	}

}

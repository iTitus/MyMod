package io.github.iTitus.MyMod.client.handler;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import io.github.iTitus.MyMod.client.render.hud.RenderClockHUD;
import io.github.iTitus.MyMod.lib.LibMod;
import io.github.iTitus.MyMod.util.TimeUtil;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AlarmHandler {

	public static ArrayList<Alarm> alarms;
	private static File alarmFile;
	private static final String TAG_ALARMS = "Alarms";

	public static void add(Alarm alarm) {
		alarms.add(alarm);
		save();
	}

	public static void editAlarm(int index, Alarm alarm) {
		alarms.set(index, alarm);
		save();
	}

	public static Alarm getAlarm(int index) {
		if (index < 0 || index >= alarms.size())
			return null;
		return alarms.get(index);
	}

	public static ArrayList<Alarm> getAlarms() {
		return alarms;
	}

	public static void init(File suggestedAlarmFile) {

		FMLCommonHandler.instance().bus().register(new AlarmHandler());

		try {

			alarmFile = suggestedAlarmFile;
			loadAlarms(alarmFile);

		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, LibMod.MOD_NAME
					+ " has a problem loading its alarm configuration!");
		}

	}

	public static void remove(int index) {
		if (index >= 0) {
			alarms.remove(index);
			save();
		}
	}

	public static void save() {

		try {
			saveAlarms();
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, LibMod.MOD_NAME
					+ " has a problem editing its alarm configuration!");
		}
	}

	private static void loadAlarms(File suggestedAlarmFile) throws Exception {

		alarms = new ArrayList<Alarm>();

		NBTTagCompound nbt = CompressedStreamTools.read(suggestedAlarmFile);
		if (nbt == null)
			return;

		NBTTagList list = nbt.getTagList(TAG_ALARMS, NBT.TAG_COMPOUND);

		for (int i = 0; i < list.tagCount(); i++) {
			alarms.add(Alarm.readFromNBT(list.getCompoundTagAt(i)));
		}

	}

	private static void saveAlarms() throws Exception {
		NBTTagList list = new NBTTagList();

		for (Alarm alarm : alarms) {
			list.appendTag(Alarm.writeToNBT(alarm));
		}

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag(TAG_ALARMS, list);
		CompressedStreamTools.safeWrite(nbt, alarmFile);
	}

	private int minLastChecked;

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (event.phase == Phase.END
				&& Minecraft.getMinecraft().thePlayer != null
				&& !TimeUtil.isMin(minLastChecked)) {
			checkAlarms();
			minLastChecked = TimeUtil.getMin();
		}
	}

	private boolean check(Alarm alarm) {
		if (alarm.isEnabled() && TimeUtil.isHour(alarm.getHour())
				&& TimeUtil.isMin(alarm.getMin()))
			return true;
		return false;
	}

	private void checkAlarms() {
		for (Alarm alarm : alarms) {
			if (check(alarm))
				showAlert(alarm);
		}

	}

	private void showAlert(Alarm alarm) {
		RenderClockHUD.add(alarm);
	}

}

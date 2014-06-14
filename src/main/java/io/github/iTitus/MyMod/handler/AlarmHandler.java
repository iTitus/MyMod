package io.github.iTitus.MyMod.handler;

import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;
import io.github.iTitus.MyMod.lib.LibMod;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class AlarmHandler {

	public static ArrayList<Alarm> alarms;
	private static File alarmFile;
	private static final String TAG_ALARMS = "Alarms";

	public static void add(Alarm alarm) {
		alarms.add(alarm);
		save();
	}

	public static ArrayList<Alarm> getAlarms() {
		return alarms;
	}

	public static void init(File suggestedAlarmFile) {

		try {

			alarmFile = suggestedAlarmFile;
			loadAlarms(alarmFile);

		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, LibMod.NAME
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
			FMLLog.log(Level.ERROR, e, LibMod.NAME
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
			list.appendTag(alarm.writeToNBT());
		}

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag(TAG_ALARMS, list);
		CompressedStreamTools.safeWrite(nbt, alarmFile);
	}

}

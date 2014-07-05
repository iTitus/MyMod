package io.github.iTitus.MyMod.common.handler;

import io.github.iTitus.MyMod.MyMod;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	public static int analog_digital = 2, color = 16711680;

	public static Configuration cfg;
	public static boolean seconds = true, am_pm = false;
	public static String separator = ":";

	private static final String CATEGORY_CLOCK = "clock",
			KEY_SEPARATOR = "separator", KEY_SECONDS = "seconds",
			KEY_AM_PM = "am_pm", KEY_ANALOG = "analog", KEY_COLOR = "color";

	public static void init(File suggestedConfigurationFile) {

		cfg = new Configuration(suggestedConfigurationFile);

		FMLCommonHandler.instance().bus().register(new ConfigHandler());

	}

	public static void loadConfig() {
		try {
			cfg.load();

			loadValues(cfg);

		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, MyMod.MOD_ID
					+ " has a problem loading its configuration!");
		} finally {
			if (cfg.hasChanged())
				cfg.save();
		}
	}

	public static void save() {

		try {
			saveValues(cfg);
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, MyMod.MOD_ID
					+ " has a problem editing its configuration!");
		} finally {
			if (cfg.hasChanged())
				cfg.save();
		}
	}

	private static void loadValues(Configuration cfg) throws Exception {

		analog_digital = cfg
				.get(CATEGORY_CLOCK, KEY_ANALOG, analog_digital,
						"The format used: 0 = None, 1 = Analog, 2 = Digital (the default), 3 = Both")
				.getInt();
		if (analog_digital < 0 || analog_digital > 3)
			analog_digital = 2;
		am_pm = cfg.get(CATEGORY_CLOCK, KEY_AM_PM, am_pm,
				"Whether the 12hrs format should be used").getBoolean(am_pm);
		separator = cfg.get(CATEGORY_CLOCK, KEY_SEPARATOR, separator,
				"The separator used in the digital clock").getString();
		seconds = cfg.get(CATEGORY_CLOCK, KEY_SECONDS, seconds,
				"Whether seconds should be displayed").getBoolean(seconds);
		color = cfg.get(CATEGORY_CLOCK, KEY_COLOR, color,
				"The color of the clock").getInt(color);

	}

	private static void saveValues(Configuration cfg) throws Exception {

		if (analog_digital < 0 || analog_digital > 3)
			analog_digital = 2;
		cfg.get(CATEGORY_CLOCK, KEY_ANALOG, analog_digital,
				"The format used: 0 = None, 1 = Analog, 2 = Digital, 3 = Both")
				.set(analog_digital);
		cfg.get(CATEGORY_CLOCK, KEY_AM_PM, am_pm,
				"Whether the 12hrs format should be used").set(am_pm);
		cfg.get(CATEGORY_CLOCK, KEY_SEPARATOR, separator,
				"The separator used in the digital clock").set(separator);
		cfg.get(CATEGORY_CLOCK, KEY_SECONDS, seconds,
				"Whether seconds should be displayed").set(seconds);
		cfg.get(CATEGORY_CLOCK, KEY_COLOR, color, "The color of the clock")
				.set(color);

	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.modID == MyMod.MOD_ID)
			loadConfig();

	}
}

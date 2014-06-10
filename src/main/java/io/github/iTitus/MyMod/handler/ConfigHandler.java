package io.github.iTitus.MyMod.handler;

import io.github.iTitus.MyMod.lib.LibMod;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class ConfigHandler {

	public static int analog_digital = 2, color = 0xFF0000;

	public static boolean seconds = true, am_pm = false;
	public static String separator = ":";
	private static final String CATEGORY_CLOCK = "CLOCK",
			KEY_SEPARATOR = "separator", KEY_SECONDS = "seconds",
			KEY_AM_PM = "am_pm", KEY_ANALOG = "analog", KEY_COLOR = "color";

	public static void init(File suggestedConfigurationFile) {

		Configuration cfg = new Configuration(suggestedConfigurationFile);
		try {
			cfg.load();

			loadValues(cfg);

		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, LibMod.NAME
					+ " has a problem loading its configuration!");
		} finally {
			if (cfg.hasChanged())
				cfg.save();
		}

	}

	private static void loadValues(Configuration cfg) throws Exception {

		analog_digital = cfg.get(CATEGORY_CLOCK, KEY_ANALOG, analog_digital,
				"The format used: 0 = None, 1 = Analog, 2 = Digital, 3 = Both")
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
				"The color of the clock").getInt();

	}
}

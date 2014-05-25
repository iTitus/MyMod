package io.github.iTitus.MyMod.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class ConfigHandler {

	public static void init(File suggestedConfigurationFile) {

		Configuration cfg = new Configuration(suggestedConfigurationFile);
		try {
			cfg.load();

			loadValues(cfg);

		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e,
					"iTitus' MyMod has a problem loading its configuration!");
		} finally {
			if (cfg.hasChanged())
				cfg.save();
		}

	}

	private static void loadValues(Configuration cfg) throws Exception {

	}

}

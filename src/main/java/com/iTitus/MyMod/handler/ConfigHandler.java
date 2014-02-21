package com.iTitus.MyMod.handler;

import java.io.File;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;

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

	private static void loadValues(Configuration cfg) throws Exception{
		
		
		
	}

}

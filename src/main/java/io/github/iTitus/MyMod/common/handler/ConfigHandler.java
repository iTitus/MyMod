package io.github.iTitus.MyMod.common.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.iTitus.MyMod.MyMod;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

import java.io.File;

public class ConfigHandler {

    public static final ConfigHandler INSTANCE = new ConfigHandler();
    private static final String CATEGORY_CLOCK = "general.clock",
            KEY_SEPARATOR = "separator", KEY_SECONDS = "seconds",
            KEY_AM_PM = "am_pm", KEY_ANALOG = "analog", KEY_COLOR = "color";
    public static int analog_digital = 2, color = 16711680;
    public static Configuration cfg;
    public static boolean seconds = true, am_pm = false;
    public static String separator = ":";

    public static void init(File suggestedConfigurationFile) {

        cfg = new Configuration(suggestedConfigurationFile);
        syncConfig(true, true);
        FMLCommonHandler.instance().bus().register(INSTANCE);

    }

    public static void saveConfig() {
        syncConfig(false, false);
    }

    private static void loadValues(Configuration cfg, boolean load)
            throws Exception {

        cfg.setCategoryComment(CATEGORY_CLOCK, "All about the clock");

        Property prop = cfg.get(CATEGORY_CLOCK, KEY_ANALOG, analog_digital);
        prop.comment = "The format used: 0 = None, 1 = Analog, 2 = Digital (the default), 3 = Both";
        prop.setMinValue(0);
        prop.setMaxValue(3);
        if (load)
            analog_digital = prop.getInt();
        else
            prop.set(analog_digital);

        prop = cfg.get(CATEGORY_CLOCK, KEY_AM_PM, am_pm);
        prop.comment = "Whether the 12hrs format should be used";
        if (load)
            am_pm = prop.getBoolean();
        else
            prop.set(am_pm);

        prop = cfg.get(CATEGORY_CLOCK, KEY_SEPARATOR, separator);
        prop.comment = "The separator used in the digital clock";
        if (load)
            separator = prop.getString();
        else
            prop.set(separator);

        prop = cfg.get(CATEGORY_CLOCK, KEY_SECONDS, seconds);
        prop.comment = "Whether seconds should be displayed";
        if (load)
            seconds = prop.getBoolean();
        else
            prop.set(seconds);

        prop = cfg.get(CATEGORY_CLOCK, KEY_COLOR, color);
        prop.comment = "The color of the clock";
        prop.setMinValue(0);
        prop.setMaxValue(16777215);
        if (load)
            color = prop.getInt();
        else
            prop.set(color);

    }

    private static void syncConfig(boolean executeLoad, boolean load) {
        try {
            if (executeLoad && !cfg.isChild)
                cfg.load();
            loadValues(cfg, load);
        } catch (Exception e) {
            FMLLog.log(Level.ERROR, e, MyMod.MOD_ID
                    + " has a problem syncing with its configuration!");
        } finally {
            if (cfg.hasChanged())
                cfg.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (event.modID == MyMod.MOD_ID)
            syncConfig(false, true);

    }
}

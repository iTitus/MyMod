package io.github.iTitus.MyMod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import io.github.iTitus.MyMod.common.proxy.CommonProxy;

@Mod(modid = MyMod.MOD_ID, version = MyMod.MOD_VERSION, guiFactory = "io.github.iTitus.MyMod.client.gui.MyModGuiFactory")
public class MyMod {

    public static final String MOD_ID = "mymod";
    public static final String MOD_VERSION = "@VERSION@";
    @Instance
    public static MyMod instance;
    @SidedProxy(clientSide = "io.github.iTitus.MyMod.client.proxy.ClientProxy", serverSide = "io.github.iTitus.MyMod.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void fingerprintViolation(FMLFingerprintViolationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void interModComms(FMLInterModComms.IMCEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {

    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {

    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {

    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {

    }
}

package io.github.iTitus.MyMod;

import io.github.iTitus.MyMod.common.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = MyMod.MOD_ID/* , guiFactory = "io.github.iTitus.MyMod.client.gui.MyModGuiFactory" */)
public class MyMod {

	@Instance
	public static MyMod instance;

	public static final String MOD_ID = "mymod";

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

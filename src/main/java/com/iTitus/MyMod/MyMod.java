package com.iTitus.MyMod;

import com.iTitus.MyMod.lib.LibMod;
import com.iTitus.MyMod.proxy.CommonProxy;

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

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION)
public class MyMod {

	@Instance(LibMod.MODID)
	public static MyMod instance;

	@SidedProxy(clientSide = "com.iTitus.MyMod.proxy.ClientProxy", serverSide = "com.iTitus.MyMod.proxy.CommonProxy")
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

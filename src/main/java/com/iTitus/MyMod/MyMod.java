package com.iTitus.MyMod;

import com.iTitus.MyMod.lib.LibMod;
import com.iTitus.MyMod.proxy.CommonProxy;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION)
public class MyMod {

	@Instance(LibMod.MODID)
	public static MyMod instance;

	@SidedProxy(clientSide = "com.iTitus.MyMod.proxy.ClientProxy", serverSide = "iTitus.MyMod.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		proxy.serverAboutToStart(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		proxy.serverStarted(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		proxy.serverStopping(event);
	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {
		proxy.serverStopped(event);
	}

	@EventHandler
	public void fingerprintViolation(FMLFingerprintViolationEvent event) {
		proxy.fingerprintViolation(event);
	}

	@EventHandler
	public void interModComms(FMLInterModComms.IMCEvent event) {
		proxy.interModComms(event);
	}
}

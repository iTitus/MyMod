package com.iTitus.MyMod;

import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.handler.ConfigHandler;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.lib.LibMod;
import com.iTitus.MyMod.network.PacketPipeline;
import com.iTitus.MyMod.proxy.CommonProxy;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION)
public class MyMod {

	@Instance(LibMod.MODID)
	public static MyMod instance;

	@SidedProxy(clientSide = "com.iTitus.MyMod.proxy.ClientProxy", serverSide = "com.iTitus.MyMod.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderers();
		PacketPipeline.INSTANCE.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {

	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {

	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {

	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {

	}

	@EventHandler
	public void fingerprintViolation(FMLFingerprintViolationEvent event) {

	}

	@EventHandler
	public void interModComms(FMLInterModComms.IMCEvent event) {

	}
}

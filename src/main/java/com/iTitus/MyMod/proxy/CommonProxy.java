package com.iTitus.MyMod.proxy;

import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.handler.ConfigHandler;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.network.PacketPipeline;

import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();
	}

	public void init(FMLInitializationEvent event) {
		PacketPipeline.INSTANCE.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();
	}

	public void serverAboutToStart(FMLServerAboutToStartEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {

	}

	public void serverStarted(FMLServerStartedEvent event) {

	}

	public void serverStopping(FMLServerStoppingEvent event) {

	}

	public void serverStopped(FMLServerStoppedEvent event) {

	}

	public void fingerprintViolation(FMLFingerprintViolationEvent event) {

	}

	public void interModComms(IMCEvent event) {

	}

}

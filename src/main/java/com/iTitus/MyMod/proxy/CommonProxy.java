package com.iTitus.MyMod.proxy;

import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.handler.ConfigHandler;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.network.PacketPipeline;
import com.iTitus.MyMod.recipe.VanillaRecipes;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void registerRenderers() {
	}

	public void registerSounds() {
	}

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();
		VanillaRecipes.init();
	}

	public void init(FMLInitializationEvent event) {
		PacketPipeline.INSTANCE.init();

		registerRenderers();
		registerSounds();
	}

	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();
	}

}

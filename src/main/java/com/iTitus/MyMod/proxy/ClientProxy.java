package com.iTitus.MyMod.proxy;

import com.iTitus.MyMod.client.render.block.RenderBlockWheel;
import com.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import com.iTitus.MyMod.lib.*;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {

	public void initRenderers() {
		LibRender.WHEEL_ID = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(RenderBlockWheel.INSTANCE);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWheel.class,
				RenderTileEntityWheel.INSTANCE);

	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		initRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		super.serverAboutToStart(event);
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
	}

	@Override
	public void serverStarted(FMLServerStartedEvent event) {
		super.serverStarted(event);
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		super.serverStopping(event);
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event) {
		super.serverStopped(event);
	}

	@Override
	public void fingerprintViolation(FMLFingerprintViolationEvent event) {
		super.fingerprintViolation(event);
	}

	@Override
	public void interModComms(IMCEvent event) {
		super.interModComms(event);
	}
}

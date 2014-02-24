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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		LibRender.WHEEL_ID = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(RenderBlockWheel.INSTANCE);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWheel.class,
				RenderTileEntityWheel.INSTANCE);

	}

}

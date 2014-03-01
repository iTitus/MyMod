package com.iTitus.MyMod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.iTitus.MyMod.MyMod;
import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.handler.ConfigHandler;
import com.iTitus.MyMod.inventory.container.ContainerWheel;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.lib.LibGUI;
import com.iTitus.MyMod.network.PacketPipeline;
import com.iTitus.MyMod.recipe.VanillaRecipes;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements IGuiHandler {

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
		NetworkRegistry.INSTANCE
				.registerGuiHandler(MyMod.instance, MyMod.proxy);

		registerRenderers();
		registerSounds();
	}

	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
		case LibGUI.WHEEL_GUI_ID:
			return new ContainerWheel(player.inventory, (TileEntityWheel) tile);

		default:
			break;
		}

		return null;
	}

}

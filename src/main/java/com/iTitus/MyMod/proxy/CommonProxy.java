package com.iTitus.MyMod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.iTitus.MyMod.MyMod;
import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.entity.gun.EntityBullet;
import com.iTitus.MyMod.handler.ConfigHandler;
import com.iTitus.MyMod.handler.CraftingHandler;
import com.iTitus.MyMod.inventory.container.ContainerWheel;
import com.iTitus.MyMod.item.ModItems;
import com.iTitus.MyMod.lib.LibGUI;
import com.iTitus.MyMod.network.PacketPipeline;
import com.iTitus.MyMod.recipe.VanillaRecipes;
import com.iTitus.MyMod.tileentity.wheel.TileEntityWheel;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy implements IGuiHandler {

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

	public void init(FMLInitializationEvent event) {
		PacketPipeline.INSTANCE.init();
		NetworkRegistry.INSTANCE
				.registerGuiHandler(MyMod.instance, MyMod.proxy);

		registerEntities();
		registerRenderers();
		registerSounds();

	}

	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityBullet.class, "", 0,
				MyMod.instance, 120, 3, true);
	}

	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();

		CraftingHandler.init();
	}

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();
		VanillaRecipes.init();
	}

	public void registerRenderers() {
	}

	public void registerSounds() {
	}

}

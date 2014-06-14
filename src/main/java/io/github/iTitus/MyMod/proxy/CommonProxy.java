package io.github.iTitus.MyMod.proxy;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.block.ModBlocks;
import io.github.iTitus.MyMod.entity.gun.EntityBullet;
import io.github.iTitus.MyMod.handler.AlarmHandler;
import io.github.iTitus.MyMod.handler.ConfigHandler;
import io.github.iTitus.MyMod.handler.CraftingHandler;
import io.github.iTitus.MyMod.handler.KeyHandler;
import io.github.iTitus.MyMod.inventory.container.ContainerWheel;
import io.github.iTitus.MyMod.item.ModItems;
import io.github.iTitus.MyMod.lib.LibGUI;
import io.github.iTitus.MyMod.lib.LibNames;
import io.github.iTitus.MyMod.network.PacketPipeline;
import io.github.iTitus.MyMod.recipe.VanillaRecipes;
import io.github.iTitus.MyMod.tileentity.wheel.TileEntityWheel;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

	public void postInit(FMLPostInitializationEvent event) {
		PacketPipeline.INSTANCE.postInit();

		CraftingHandler.init();
		KeyHandler.init();
	}

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		AlarmHandler.init(new File(event.getSuggestedConfigurationFile()
				.getParentFile() + "/mymod-alarms.dat"));

		ModBlocks.init();
		ModItems.init();
		VanillaRecipes.init();
	}

	public void registerEntities() {

		int id = -1;

		EntityRegistry.registerModEntity(EntityBullet.class,
				LibNames.BULLET_NAME, id++, MyMod.instance, 120, 3, true);
	}

	public void registerRenderers() {
	}

	public void registerSounds() {
	}
}

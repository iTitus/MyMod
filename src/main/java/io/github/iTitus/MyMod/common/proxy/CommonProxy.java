package io.github.iTitus.MyMod.common.proxy;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.block.ModBlocks;
import io.github.iTitus.MyMod.common.entity.gun.EntityBullet;
import io.github.iTitus.MyMod.common.handler.ConfigHandler;
import io.github.iTitus.MyMod.common.handler.CraftingHandler;
import io.github.iTitus.MyMod.common.inventory.container.ContainerWheel;
import io.github.iTitus.MyMod.common.item.ModItems;
import io.github.iTitus.MyMod.common.lib.LibGUI;
import io.github.iTitus.MyMod.common.lib.LibNames;
import io.github.iTitus.MyMod.common.network.NetworkHandler;
import io.github.iTitus.MyMod.common.recipe.VanillaRecipes;
import io.github.iTitus.MyMod.common.tileentity.wheel.TileEntityWheel;
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
		NetworkRegistry.INSTANCE
				.registerGuiHandler(MyMod.instance, MyMod.proxy);

		registerEntities();
		registerRenderers();
		registerSounds();

	}

	public void postInit(FMLPostInitializationEvent event) {

		CraftingHandler.init();

	}

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		NetworkHandler.init();

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

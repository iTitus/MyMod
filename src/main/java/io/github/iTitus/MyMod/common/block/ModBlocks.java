package io.github.iTitus.MyMod.common.block;

import io.github.iTitus.MyMod.common.block.display.BlockDisplay;
import io.github.iTitus.MyMod.common.block.display.ItemBlockDisplay;
import io.github.iTitus.MyMod.common.block.sphere.BlockSphere;
import io.github.iTitus.MyMod.common.block.timeshifter.BlockTimeshifter;
import io.github.iTitus.MyMod.common.block.wheel.BlockWheel;
import io.github.iTitus.MyMod.common.block.wheel.ItemBlockWheel;
import io.github.iTitus.MyMod.common.block.zero.BlockZero;
import io.github.iTitus.MyMod.common.lib.LibNames;
import io.github.iTitus.MyMod.common.tileentity.display.TileEntityDisplay;
import io.github.iTitus.MyMod.common.tileentity.sphere.TileEntitySphere;
import io.github.iTitus.MyMod.common.tileentity.wheel.TileEntityWheel;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block zero, daymaker, wheel, sphere, display;

	public static void init() {
		zero = new BlockZero();
		GameRegistry.registerBlock(zero, zero.getUnlocalizedName());

		daymaker = new BlockTimeshifter();
		GameRegistry.registerBlock(daymaker, daymaker.getUnlocalizedName());

		wheel = new BlockWheel();
		GameRegistry.registerBlock(wheel, ItemBlockWheel.class,
				wheel.getUnlocalizedName());

		sphere = new BlockSphere();
		GameRegistry.registerBlock(sphere, sphere.getUnlocalizedName());

		display = new BlockDisplay();
		GameRegistry.registerBlock(display, ItemBlockDisplay.class,
				display.getUnlocalizedName());

		initTileEntities();

	}

	public static void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityWheel.class,
				LibNames.WHEEL_TE_NAME);

		GameRegistry.registerTileEntity(TileEntitySphere.class,
				LibNames.SPHERE_TE);

		GameRegistry.registerTileEntity(TileEntityDisplay.class,
				LibNames.DISPLAY_TE);
	}

}

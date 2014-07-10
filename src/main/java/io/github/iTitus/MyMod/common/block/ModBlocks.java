package io.github.iTitus.MyMod.common.block;

import io.github.iTitus.MyMod.MyMod;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MyMod.MOD_ID)
public class ModBlocks {

	public static final BlockTimeshifter daymaker = new BlockTimeshifter();
	public static final BlockDisplay display = new BlockDisplay();
	public static final BlockSphere sphere = new BlockSphere();
	public static final BlockWheel wheel = new BlockWheel();
	public static final BlockZero zero = new BlockZero();

	public static void init() {

		GameRegistry.registerBlock(zero, zero.getUnlocalizedName());

		GameRegistry.registerBlock(daymaker, daymaker.getUnlocalizedName());

		GameRegistry.registerBlock(wheel, ItemBlockWheel.class,
				wheel.getUnlocalizedName());

		GameRegistry.registerBlock(sphere, sphere.getUnlocalizedName());

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

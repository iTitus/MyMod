package com.iTitus.MyMod.block;

import net.minecraft.block.Block;

import com.iTitus.MyMod.lib.LibNames;
import com.iTitus.MyMod.tileentiy.*;

import cpw.mods.fml.common.registry.*;

public class ModBlocks {

	public static Block zero, daymaker, wheel;

	public static void init() {
		zero = new BlockZero();
		GameRegistry.registerBlock(zero, EnumBlockType.ZERO.name);

		daymaker = new BlockTimeshifter();
		GameRegistry.registerBlock(daymaker, EnumBlockType.TIMESHIFTER.name);

		wheel = new BlockWheel();
		GameRegistry.registerBlock(wheel, EnumBlockType.WHEEL.name);

		initTileEntities();

	}

	public static void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityWheel.class, LibNames.WHEEL_TE_NAME);
	}

}

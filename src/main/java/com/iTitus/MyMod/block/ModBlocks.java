package com.iTitus.MyMod.block;

import net.minecraft.block.Block;

import com.iTitus.MyMod.item.ItemWheel;
import com.iTitus.MyMod.lib.LibNames;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block zero, daymaker, wheel;

	public static void init() {
		zero = new BlockZero();
		GameRegistry.registerBlock(zero, zero.getUnlocalizedName());

		daymaker = new BlockTimeshifter();
		GameRegistry.registerBlock(daymaker, daymaker.getUnlocalizedName());

		wheel = new BlockWheel();
		GameRegistry.registerBlock(wheel, ItemWheel.class,
				wheel.getUnlocalizedName());

		initTileEntities();

	}

	public static void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityWheel.class,
				LibNames.WHEEL_TE_NAME);
	}

}

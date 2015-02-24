package io.github.iTitus.MyMod.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
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

@ObjectHolder(MyMod.MOD_ID)
public class ModBlocks {

    public static final MyBlock timeshifter = new BlockTimeshifter();
    public static final MyBlock display = new BlockDisplay();
    public static final MyBlock sphere = new BlockSphere();
    public static final MyBlock wheel = new BlockWheel();
    public static final MyBlock zero = new BlockZero();

    public static void init() {

        GameRegistry.registerBlock(zero, LibNames.ZERO_NAME);

        GameRegistry.registerBlock(timeshifter, LibNames.TIMESHIFTER_NAME);

        GameRegistry.registerBlock(wheel, ItemBlockWheel.class,
                LibNames.WHEEL_NAME);

        GameRegistry.registerBlock(sphere, LibNames.SPHERE_NAME);

        GameRegistry.registerBlock(display, ItemBlockDisplay.class,
                LibNames.DISPLAY_NAME);

        initTileEntities();

    }

    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileEntityWheel.class,
                LibNames.WHEEL_TE_NAME);

        GameRegistry.registerTileEntity(TileEntitySphere.class,
                LibNames.SPHERE_TE_NAME);

        GameRegistry.registerTileEntity(TileEntityDisplay.class,
                LibNames.DISPLAY_TE_NAME);
    }

}

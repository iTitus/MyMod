package io.github.ititus.mymod.proxy;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.api.recipe.RecipeManager;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import io.github.ititus.mymod.block.*;
import io.github.ititus.mymod.fluid.FluidMilk;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.init.ModFluids;
import io.github.ititus.mymod.item.ItemBackpack;
import io.github.ititus.mymod.item.ItemDust;
import io.github.ititus.mymod.item.ItemSideConfigurator;
import io.github.ititus.mymod.item.block.ItemBlockBase;
import io.github.ititus.mymod.recipe.pulverizer.PulverizerRecipe;
import io.github.ititus.mymod.tile.*;
import io.github.ititus.mymod.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID)
public class CommonProxy {

    public static void registerTile(Class<? extends TileEntity> tileClass, String name) {
        GameRegistry.registerTileEntity(tileClass, MyMod.MOD_ID + ":tileentity." + name);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        if (!MyMod.MINIMAL_MODE) {
            FluidRegistry.registerFluid(new FluidMilk());

            event.getRegistry().registerAll(new BlockFallGen(), new BlockEnergyCell(), new BlockPulverizer(), new BlockTank(), new BlockMultiChest());

            registerTile(TileFallGen.class, MyMod.FALL_GEN);
            registerTile(TileEnergyCell.class, MyMod.ENERGY_CELL);
            registerTile(TilePulverizer.class, MyMod.PULVERIZER);
            registerTile(TileTank.class, MyMod.TANK);
            registerTile(TileMultiChest.class, MyMod.MULTI_CHEST);
        }

        ModFluids.fluidMilk = FluidRegistry.getFluid(MyMod.MILK);
        if (ModFluids.fluidMilk != null) {
            FluidRegistry.addBucketForFluid(ModFluids.fluidMilk);
            event.getRegistry().register(new BlockFluidMilk());
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        if (!MyMod.MINIMAL_MODE) {
            event.getRegistry().registerAll(new ItemDust(), new ItemSideConfigurator(), new ItemBackpack());

            if (ModBlocks.fallGen != null) {
                event.getRegistry().register(ModBlocks.fallGen.getItemBlock());
            }
            if (ModBlocks.energyCell != null) {
                event.getRegistry().register(ModBlocks.energyCell.getItemBlock());
            }
            if (ModBlocks.pulverizer != null) {
                event.getRegistry().register(ModBlocks.pulverizer.getItemBlock());
            }
            if (ModBlocks.tank != null) {
                event.getRegistry().register(ModBlocks.tank.getItemBlock());
            }
            if (ModBlocks.multiChest != null) {
                event.getRegistry().register(ModBlocks.multiChest.getItemBlock());
            }
            if (ModBlocks.blockMilk != null) {
                event.getRegistry().register(new ItemBlockBase(ModBlocks.blockMilk));
            }
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MyMod.MOD_ID)) {
            ConfigManager.sync(MyMod.MOD_ID, Config.Type.INSTANCE);
        }
    }

    public void preInit() {
    }

    public void init() {
        addPulverizerRecipe(new ItemStack(Items.BONE), ItemUtil.getDyeStack(EnumDyeColor.WHITE, 6));
        addPulverizerRecipe("sugarcane", new ItemStack(Items.SUGAR, 2));
        addPulverizerRecipe(ItemUtil.getWoolStack(null), new ItemStack(Items.STRING, 4));
    }

    public void postInit() {
    }

    public void addPulverizerRecipe(IPulverizerRecipe recipe) {
        RecipeManager.pulverizer.register(recipe);
    }

    public void addPulverizerRecipe(ItemStack in, ItemStack out1) {
        addPulverizerRecipe(PulverizerRecipe.builder().in(in).out1(out1).build());
    }

    public void addPulverizerRecipe(String in, ItemStack out1) {
        addPulverizerRecipe(PulverizerRecipe.builder().in(in).out1(out1).build());
    }
}

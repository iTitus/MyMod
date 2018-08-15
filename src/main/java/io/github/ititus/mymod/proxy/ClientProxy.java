package io.github.ititus.mymod.proxy;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.api.dust.DustManager;
import io.github.ititus.mymod.api.dust.IDust;
import io.github.ititus.mymod.client.render.tile.TESRTank;
import io.github.ititus.mymod.command.CommandMyMod;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.init.ModFluids;
import io.github.ititus.mymod.init.ModItems;
import io.github.ititus.mymod.tile.TileTank;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MyMod.MOD_ID)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        if (ModBlocks.fallGen != null) {
            registerStandardInventoryVariant(ModBlocks.fallGen);
        }

        if (ModBlocks.energyCell != null) {
            registerStandardInventoryVariant(ModBlocks.energyCell);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.energyCell), 1, new ModelResourceLocation(new ResourceLocation(MyMod.MOD_ID, "creative_energy_cell"), "inventory"));
        }

        if (ModBlocks.pulverizer != null) {
            registerStandardInventoryVariant(ModBlocks.pulverizer);
        }
        if (ModBlocks.multiChest != null) {
            registerStandardInventoryVariant(ModBlocks.multiChest);
        }

        if (ModBlocks.tank != null) {
            registerStandardInventoryVariant(ModBlocks.tank);
            ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TESRTank());
        }

        if (ModItems.dust != null) {
            ModelResourceLocation modResLoc = new ModelResourceLocation(ModItems.dust.getRegistryName(), "inventory");
            ModelLoader.setCustomMeshDefinition(ModItems.dust, stack -> modResLoc);
            ModelLoader.registerItemVariants(ModItems.dust, modResLoc);
        }

        if (ModItems.sideConfigurator != null) {
            registerStandardInventoryVariant(ModItems.sideConfigurator);
        }

        if (ModItems.backpack != null) {
            registerStandardInventoryVariant(ModItems.backpack);
        }

        if (ModFluids.fluidMilk != null) {
            registerFluidRender(ModFluids.fluidMilk);
        }
    }

    private static void registerFluidRender(Fluid fluid) {
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        ModelResourceLocation modResLoc = new ModelResourceLocation(MyMod.MOD_ID + ":fluids", fluid.getName());
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return modResLoc;
            }
        });
        ModelLoader.setCustomMeshDefinition(item, stack -> modResLoc);
        ModelLoader.registerItemVariants(item, modResLoc);
    }

    private static void registerStandardInventoryVariant(Block block) {
        registerStandardInventoryVariant(Item.getItemFromBlock(block));
    }

    private static void registerStandardInventoryVariant(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void preInit() {
        super.preInit();
        ClientCommandHandler.instance.registerCommand(new CommandMyMod(true));
    }

    @Override
    public void init() {
        super.init();
        if (ModItems.dust != null) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
                IDust dust = DustManager.dusts.get(stack);
                return dust != null ? dust.getColor() : 0xFFFFFF;

            }, ModItems.dust);
        }
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}

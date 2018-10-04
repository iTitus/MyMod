package io.github.ititus.mymod;

import io.github.ititus.mymod.api.dust.DustManager;
import io.github.ititus.mymod.api.recipe.RecipeManager;
import io.github.ititus.mymod.command.CommandMyMod;
import io.github.ititus.mymod.dust.DustRegistry;
import io.github.ititus.mymod.dust.Dusts;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.network.NetworkHandler;
import io.github.ititus.mymod.proxy.CommonProxy;
import io.github.ititus.mymod.recipe.pulverizer.PulverizerRecipeManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
@Mod(modid = MyMod.MOD_ID, name = MyMod.MOD_NAME, version = MyMod.MOD_VERSION, guiFactory = MyMod.GUI_FACTORY, dependencies = MyMod.DEPENDENCIES)
public class MyMod {

    public static final String MOD_ID = "mymod", MOD_NAME = "MyMod", MOD_VERSION = "@MODVERSION@";
    public static final String CLIENT_PROXY = "io.github.ititus.mymod.proxy.ClientProxy", SERVER_PROXY = "io.github.ititus.mymod.proxy.CommonProxy", GUI_FACTORY = "io.github.ititus.mymod.client.gui.ModGuiFactory";
    public static final String DEPENDENCIES = "after:jei";

    public static final boolean MINIMAL_MODE = false;

    public static final String FALL_GEN = "fall_gen";
    public static final String ENERGY_CELL = "energy_cell";
    public static final String PULVERIZER = "pulverizer";
    public static final String TANK = "tank";
    public static final String MULTI_CHEST = "multi_chest";
    public static final String MILK = "milk";

    public static final String DUST = "dust";
    public static final String SIDE_CONFIGURATOR = "side_configurator";
    public static final String BACKPACK = "backpack";

    @Mod.Instance
    public static MyMod instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        // BackpackManager.getServerInstance().load(event.getServer());
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandMyMod(false));
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        // BackpackManager.getServerInstance().clear();
    }

    @Mod.EventHandler
    public void fingerprintViolation(FMLFingerprintViolationEvent event) {
    }

    @Mod.EventHandler
    public void onIMC(FMLInterModComms.IMCEvent event) {
    }

}

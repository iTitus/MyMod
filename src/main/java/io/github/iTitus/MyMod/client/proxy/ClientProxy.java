package io.github.iTitus.MyMod.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.iTitus.MyMod.client.gui.GuiContainerWheel;
import io.github.iTitus.MyMod.client.gui.GuiScreenClockConfig;
import io.github.iTitus.MyMod.client.handler.AlarmHandler;
import io.github.iTitus.MyMod.client.handler.KeyHandler;
import io.github.iTitus.MyMod.client.render.block.RenderBlockWheel;
import io.github.iTitus.MyMod.client.render.entity.RenderEntityBullet;
import io.github.iTitus.MyMod.client.render.hud.RenderClockHUD;
import io.github.iTitus.MyMod.client.render.item.RenderItemGun;
import io.github.iTitus.MyMod.client.render.tileentity.RenderTileEntityDisplay;
import io.github.iTitus.MyMod.client.render.tileentity.RenderTileEntitySphere;
import io.github.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import io.github.iTitus.MyMod.common.entity.gun.EntityBullet;
import io.github.iTitus.MyMod.common.inventory.container.ContainerWheel;
import io.github.iTitus.MyMod.common.item.ModItems;
import io.github.iTitus.MyMod.common.lib.LibGUI;
import io.github.iTitus.MyMod.common.lib.LibRender;
import io.github.iTitus.MyMod.common.proxy.CommonProxy;
import io.github.iTitus.MyMod.common.tileentity.display.TileEntityDisplay;
import io.github.iTitus.MyMod.common.tileentity.sphere.TileEntitySphere;
import io.github.iTitus.MyMod.common.tileentity.wheel.TileEntityWheel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {

        TileEntity tile = world.getTileEntity(x, y, z);

        switch (ID) {
            case LibGUI.WHEEL_GUI_ID:
                return new GuiContainerWheel(new ContainerWheel(player.inventory,
                        (TileEntityWheel) tile));

            case LibGUI.CLOCK_CONFIG_GUI:
                return new GuiScreenClockConfig();

            default:
                break;
        }

        return null;
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        KeyHandler.init();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        AlarmHandler.init(new File(event.getModConfigurationDirectory()
                + "/mymod-alarms.dat"));
    }

    @Override
    public void registerRenderers() {

        LibRender.WHEEL_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(RenderBlockWheel.INSTANCE);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWheel.class,
                RenderTileEntityWheel.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(ModItems.gun,
                RenderItemGun.INSTANCE);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySphere.class,
                RenderTileEntitySphere.INSTANCE);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplay.class,
                RenderTileEntityDisplay.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class,
                RenderEntityBullet.INSTANCE);

        RenderClockHUD.init();

    }

}

package io.github.iTitus.MyMod.proxy;

import io.github.iTitus.MyMod.client.gui.GUIWheel;
import io.github.iTitus.MyMod.client.render.block.RenderBlockWheel;
import io.github.iTitus.MyMod.client.render.entity.RenderEntityBullet;
import io.github.iTitus.MyMod.client.render.item.ItemGunRenderer;
import io.github.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import io.github.iTitus.MyMod.entity.gun.EntityBullet;
import io.github.iTitus.MyMod.inventory.container.ContainerWheel;
import io.github.iTitus.MyMod.item.ModItems;
import io.github.iTitus.MyMod.lib.LibGUI;
import io.github.iTitus.MyMod.lib.LibRender;
import io.github.iTitus.MyMod.tileentity.wheel.TileEntityWheel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
		case LibGUI.WHEEL_GUI_ID:
			return new GUIWheel(new ContainerWheel(player.inventory,
					(TileEntityWheel) tile));

		default:
			break;
		}

		return null;
	}

	@Override
	public void registerRenderers() {
		LibRender.WHEEL_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(RenderBlockWheel.INSTANCE);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWheel.class,
				RenderTileEntityWheel.INSTANCE);

		MinecraftForgeClient.registerItemRenderer(ModItems.gun,
				ItemGunRenderer.INSTANCE);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderEntityBullet.INSTANCE);

	}

}

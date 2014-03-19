package com.iTitus.MyMod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.iTitus.MyMod.client.gui.GUIWheel;
import com.iTitus.MyMod.client.render.block.RenderBlockWheel;
import com.iTitus.MyMod.client.render.tileentity.RenderTileEntityWheel;
import com.iTitus.MyMod.inventory.container.ContainerWheel;
import com.iTitus.MyMod.lib.LibGUI;
import com.iTitus.MyMod.lib.LibRender;
import com.iTitus.MyMod.tileentiy.TileEntityWheel;

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

	}

}

package io.github.iTitus.MyMod.client.render.hud;

import io.github.iTitus.MyMod.handler.ConfigHandler;
import io.github.iTitus.MyMod.helper.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderClockHUD {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new RenderClockHUD());
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event) {
		if (event.type == ElementType.ALL
				&& (Minecraft.getMinecraft().currentScreen == null
						|| Minecraft.getMinecraft().currentScreen instanceof GuiChat || Minecraft
							.getMinecraft().currentScreen instanceof InventoryEffectRenderer)) {
			switch (ConfigHandler.analog_digital) {
			case 0: // None
				return;

			case 1: // Analog
				Minecraft.getMinecraft().fontRenderer.drawString(
						"FANCY CLOCK!", 8, 6, ConfigHandler.color);
				break;

			case 2: // Digital
				Minecraft.getMinecraft().fontRenderer.drawString(
						TimeHelper.getTime(), 8, 6, ConfigHandler.color);
				break;

			case 3: // Both
				Minecraft.getMinecraft().fontRenderer.drawString(
						"FANCY CLOCK! " + TimeHelper.getTime(), 8, 6,
						ConfigHandler.color);
				break;
			}
		}
	}

}

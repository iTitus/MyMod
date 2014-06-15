package io.github.iTitus.MyMod.handler;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.client.render.hud.RenderAlarmHUD;
import io.github.iTitus.MyMod.client.render.hud.RenderClockHUD;
import io.github.iTitus.MyMod.lib.LibGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler {

	private static final String[] desc = { "key.openClockGUI.desc" };
	private static final int[] keyValues = { Keyboard.KEY_C };

	public static void init() {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	private final KeyBinding[] keys;

	public KeyHandler() {

		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; i++) {
			keys[i] = new KeyBinding(desc[i], keyValues[i],
					"key.categories.mymod");
			ClientRegistry.registerKeyBinding(keys[i]);
		}

	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		if (Minecraft.getMinecraft().currentScreen == null) {

			for (int i = 0; i < desc.length; i++) {

				if (keys[i].isPressed()) {
					if (!RenderAlarmHUD.getInstance().isShowing())
						Minecraft.getMinecraft().thePlayer.openGui(
								MyMod.instance, LibGUI.CLOCK_CONFIG_GUI,
								Minecraft.getMinecraft().theWorld,
								(int) Minecraft.getMinecraft().thePlayer.posX,
								(int) Minecraft.getMinecraft().thePlayer.posY,
								(int) Minecraft.getMinecraft().thePlayer.posZ);
					else
						RenderAlarmHUD.getInstance().next();
				}

			}

		}
	}
}

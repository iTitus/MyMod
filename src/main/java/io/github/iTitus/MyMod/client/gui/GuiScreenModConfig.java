package io.github.iTitus.MyMod.client.gui;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.client.handler.KeyHandler;
import io.github.iTitus.MyMod.common.handler.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenModConfig extends GuiConfig {

	public GuiScreenModConfig(GuiScreen parent) {
		super(
				parent,
				new ConfigElement(
						ConfigHandler.cfg
								.getCategory(Configuration.CATEGORY_GENERAL))
						.getChildElements(),
				MyMod.MOD_ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(ConfigHandler.cfg.toString()),
				"Some properties need a restart!"
						+ "\n"
						+ "You can also (and better) edit the clock properties by pressing '"
						+ Keyboard.getKeyName(KeyHandler.getKeyBinding(
								KeyHandler.KEYBINDING_CLOCK).getKeyCode())
						+ "' ingame");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}

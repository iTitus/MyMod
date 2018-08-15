package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.MyMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiModConfig extends GuiConfig {

    public GuiModConfig(GuiScreen parentScreen) {
        super(parentScreen, MyMod.MOD_ID, MyMod.MOD_NAME);
    }
}

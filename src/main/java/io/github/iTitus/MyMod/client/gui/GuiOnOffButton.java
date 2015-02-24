package io.github.iTitus.MyMod.client.gui;

import net.minecraft.util.StatCollector;

public class GuiOnOffButton extends GuiSwitchButton {

    public GuiOnOffButton(int id, int x, int y, int width, int height,
                          String title, boolean defaultValue) {
        super(id, x, y, width, height, title, defaultValue ? 1 : 0,
                new String[]{StatCollector.translateToLocal("gui.off"),
                        StatCollector.translateToLocal("gui.on")});
    }

    public GuiOnOffButton(int id, int x, int y, String title,
                          boolean defaultValue) {
        this(id, x, y, 200, 20, title, defaultValue);
    }

    public boolean getCurrentValue() {
        return (getCurrentIndex() == 0) ? false : true;
    }

}

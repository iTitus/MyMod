package io.github.iTitus.MyMod.client.gui;

import net.minecraft.util.StatCollector;

public class GuiOnOffButton extends GuiSwitchButton {

	public GuiOnOffButton(int id, int x, int y, String title,
			boolean defaultValue) {
		super(id, x, y, title, defaultValue ? 1 : 0, new String[] {
				StatCollector.translateToLocal("gui.off"),
				StatCollector.translateToLocal("gui.on") });
	}

	public boolean getCurrentValue() {
		return (getCurrentIndex() == 0) ? false : true;
	}

}

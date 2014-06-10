package io.github.iTitus.MyMod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSwitchButton extends GuiButton {

	private int currentIndex;
	private String[] options;
	private String title;

	public GuiSwitchButton(int id, int x, int y, String title,
			int defaultIndex, String... options) {
		super(id, x, y, title);
		this.options = options;
		this.title = title;
		currentIndex = defaultIndex;
		displayString = title + ": " + this.options[currentIndex];
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int x, int y) {

		boolean ret = super.mousePressed(mc, x, y);

		if (ret)
			nextIndex();

		return ret;
	}

	public void nextIndex() {
		currentIndex++;
		if (currentIndex >= options.length)
			currentIndex = 0;
		displayString = title + ": " + options[currentIndex];
	}
}

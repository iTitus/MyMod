package io.github.iTitus.MyMod.client.gui;

public class GuiOnOffButton extends GuiSwitchButton {

	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param title
	 * @param defaultIndex
	 *            0 -> OFF, 1 -> ON
	 */
	public GuiOnOffButton(int id, int x, int y, String title,
			boolean defaultValue) {
		super(id, x, y, title, ((defaultValue) ? 1 : 0), new String[] { "OFF",
				"ON" });
	}

	public boolean getCurrentValue() {
		return (getCurrentIndex() == 0) ? false : true;
	}

}

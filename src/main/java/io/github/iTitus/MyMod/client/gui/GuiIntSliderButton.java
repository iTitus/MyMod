package io.github.iTitus.MyMod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIntSliderButton extends GuiButton {

	private boolean isSliding;
	private String label;
	private int position, minPos, maxPos;

	public GuiIntSliderButton(int id, int x, int y, int width, int height,
			String label, int startPos, int minPos, int maxPos) {
		super(id, x, y, width, height, label);
		this.label = label;
		this.position = startPos;
		this.minPos = minPos;
		this.maxPos = maxPos;
		displayString = label + ": " + position;

	}

	public GuiIntSliderButton(int id, int x, int y, String label, int startPos,
			int minPos, int maxPos) {
		this(id, x, y, 200, 20, label, startPos, minPos, maxPos);
	}

	@Override
	public int getHoverState(boolean b) {
		return 0;
	}

	public int getSliderPosition() {
		return position;
	}

	public boolean isSliding() {
		return true;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int x, int y) {
		if (super.mousePressed(mc, x, y)) {
			position = (int) (((x - (xPosition + 4F)) / ((width - 8F))) * (maxPos));

			if (position < minPos) {
				position = minPos;
			}

			if (position > maxPos) {
				position = maxPos;
			}

			displayString = label + ": " + position;
			isSliding = true;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		isSliding = false;
	}

	@Override
	protected void mouseDragged(Minecraft mc, int x, int y) {
		if (isSliding) {
			position = (int) (((x - (xPosition + 4F)) / ((width - 8F))) * (maxPos));

			if (position < minPos) {
				position = minPos;
			}

			if (position > maxPos) {
				position = maxPos;
			}

			displayString = label + ": " + position;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(
				(int) (xPosition + (((float) position / maxPos) * (width - 8))),
				yPosition, 0, 66, 4, 20);
		drawTexturedModalRect((int) (xPosition
				+ (((float) position / maxPos) * (width - 8)) + 4), yPosition,
				196, 66, 4, 20);
	}

}

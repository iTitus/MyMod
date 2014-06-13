package io.github.iTitus.MyMod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarm implements IGuiListEntry {

	public static class Alarm {

	}

	private Alarm alarm;

	public GuiAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	@Override
	public void drawEntry(int var1, int var2, int var3, int width, int var5,
			Tessellator tess, int var7, int var8, boolean var9) {
		Minecraft.getMinecraft().fontRenderer.drawString("An alarm",
				var2 + 32 + 3, var3 + 1, 16777215);
	}

	@Override
	public boolean mousePressed(int var1, int var2, int var3, int var4,
			int var5, int var6) {
		return false;
	}

	@Override
	public void mouseReleased(int var1, int var2, int var3, int var4, int var5,
			int var6) {

	}

}

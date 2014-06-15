package io.github.iTitus.MyMod.client.render.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.Queues;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import io.github.iTitus.MyMod.client.gui.GuiAlarm.Alarm;

@SideOnly(Side.CLIENT)
public class RenderAlarmHUD {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(getInstance());
		FMLCommonHandler.instance().bus().register(getInstance());
	}

	private ConcurrentLinkedQueue<Alarm> alarmsToRender;
	private Alarm currentAlarm, previousAlarm;

	public RenderAlarmHUD() {
		alarmsToRender = new ConcurrentLinkedQueue<Alarm>();
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event) {

		if (alarmsToRender.size() != 0 && currentAlarm != null
				&& event.type == ElementType.ALL) {

			Minecraft.getMinecraft().fontRenderer
					.drawString(
							currentAlarm.getTitle(),
							(Minecraft.getMinecraft().displayWidth / 2)
									- (Minecraft.getMinecraft().fontRenderer
											.getStringWidth(currentAlarm
													.getTitle()) / 2),
							(Minecraft.getMinecraft().displayHeight / 2)
									- (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2),
							16777215);

		}

	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

	}

	public static RenderAlarmHUD getInstance() {
		return new RenderAlarmHUD();

	}

	public void add(Alarm alarm) {
		alarmsToRender.offer(alarm);
		if (currentAlarm == null)
			next();
	}

	public void next() {
		if (currentAlarm != null)
			currentAlarm.setEnabled(false);
		previousAlarm = currentAlarm;
		currentAlarm = alarmsToRender.poll();
		if (previousAlarm != null && previousAlarm.isRepeating())
			previousAlarm.setEnabled(true);
	}

	public boolean isShowing() {
		return currentAlarm != null;
	}
}

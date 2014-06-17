package io.github.iTitus.MyMod.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public abstract class AbstractMessage<REQ extends IMessage> implements
		IMessage, IMessageHandler<REQ, IMessage> {

	/**
	 * Handle a packet on the client side.
	 * 
	 * @param message
	 *            the message
	 * @param player
	 *            the player reference
	 */
	public abstract void handleClientSide(REQ message, EntityPlayer player);

	/**
	 * Handle a packet on the server side.
	 * 
	 * @param message
	 *            the message
	 * @param player
	 *            the player reference
	 */
	public abstract void handleServerSide(REQ message, EntityPlayer player);

	@Override
	public REQ onMessage(REQ message, MessageContext ctx) {

		if (ctx.side == Side.SERVER)
			handleServerSide(message, ctx.getServerHandler().playerEntity);
		else
			handleClientSide(message, Minecraft.getMinecraft().thePlayer);

		return null;
	}
}
package io.github.iTitus.MyMod.network;

import io.github.iTitus.MyMod.lib.LibMod;
import io.github.iTitus.MyMod.network.message.DoubleCoordMessage;
import io.github.iTitus.MyMod.network.message.IntCoordMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(LibMod.MOD_ID);
	private static int discriminator;

	public static void init() {
	}

	/**
	 * Registers a message handled by the {@link Side#CLIENT}
	 * 
	 * @param messageHandler
	 * @param requestMessageType
	 */
	public static void registerClientMessage(
			Class<IMessageHandler<IMessage, IMessage>> messageHandler,
			Class<IMessage> requestMessageType) {
		INSTANCE.registerMessage(messageHandler, requestMessageType,
				discriminator++, Side.CLIENT);
	}

	/**
	 * Registers a message handled by the {@link Side#SERVER}
	 * 
	 * @param messageHandler
	 * @param requestMessageType
	 */
	public static void registerServerMessage(
			Class<IMessageHandler<IMessage, IMessage>> messageHandler,
			Class<IMessage> requestMessageType) {
		INSTANCE.registerMessage(messageHandler, requestMessageType,
				discriminator++, Side.SERVER);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}

	public static void sendToAll(IMessage message) {
		INSTANCE.sendToAll(message);
	}

	public static void sendToAllAround(DoubleCoordMessage message, World world) {
		sendToAllAround(message, message.getTargetPoint(world));
	}

	public static void sendToAllAround(DoubleCoordMessage message, World world,
			double distance) {
		sendToAllAround(message, message.getTargetPoint(world, distance));
	}

	public static void sendToAllAround(IMessage message,
			NetworkRegistry.TargetPoint point) {
		INSTANCE.sendToAllAround(message, point);
	}

	public static void sendToAllAround(IntCoordMessage message, World world) {
		sendToAllAround(message, message.getTargetPoint(world));
	}

	public static void sendToAllAround(IntCoordMessage message, World world,
			double distance) {
		sendToAllAround(message, message.getTargetPoint(world, distance));
	}

	public static void sendToDimension(IMessage message, int dimensionId) {
		INSTANCE.sendToDimension(message, dimensionId);
	}

	public static void sendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}
}

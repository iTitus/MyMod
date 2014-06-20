package io.github.iTitus.MyMod.network;

import io.github.iTitus.MyMod.lib.LibMod;
import io.github.iTitus.MyMod.network.message.MessageDoubleCoord;
import io.github.iTitus.MyMod.network.message.MessageIntCoord;
import io.github.iTitus.MyMod.network.message.MessageMyTileEntity;
import io.github.iTitus.MyMod.network.message.MessageTileEntityDisplay;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(LibMod.MOD_ID);
	private static int discriminator = -1;

	public static void init() {
		INSTANCE.registerMessage(MessageMyTileEntity.class,
				MessageMyTileEntity.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityDisplay.class,
				MessageTileEntityDisplay.class, discriminator++, Side.CLIENT);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}

	public static void sendToAll(IMessage message) {
		INSTANCE.sendToAll(message);
	}

	public static void sendToAllAround(IMessage message,
			NetworkRegistry.TargetPoint point) {
		INSTANCE.sendToAllAround(message, point);
	}

	public static void sendToAllAround(MessageDoubleCoord message, World world) {
		sendToAllAround(message, message.getTargetPoint(world));
	}

	public static void sendToAllAround(MessageDoubleCoord message, World world,
			double distance) {
		sendToAllAround(message, message.getTargetPoint(world, distance));
	}

	public static void sendToAllAround(MessageIntCoord message, World world) {
		sendToAllAround(message, message.getTargetPoint(world));
	}

	public static void sendToAllAround(MessageIntCoord message, World world,
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

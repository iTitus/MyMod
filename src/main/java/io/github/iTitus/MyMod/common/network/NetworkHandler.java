package io.github.iTitus.MyMod.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.network.message.MessageDoubleCoord;
import io.github.iTitus.MyMod.common.network.message.MessageIntCoord;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
            .newSimpleChannel(MyMod.MOD_ID);
    private static int discriminator = -1;

    public static void init() {

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

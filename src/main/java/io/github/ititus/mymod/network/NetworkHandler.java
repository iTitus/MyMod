package io.github.ititus.mymod.network;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.network.message.MessageContainerUpdate;
import io.github.ititus.mymod.network.message.MessageGuiUpdate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MyMod.MOD_ID);

    public static final void init() {
        int discriminator = 0;
        INSTANCE.registerMessage(MessageContainerUpdate.class, MessageContainerUpdate.class, discriminator++, Side.CLIENT);
        INSTANCE.registerMessage(MessageGuiUpdate.class, MessageGuiUpdate.class, discriminator++, Side.SERVER);
    }

}

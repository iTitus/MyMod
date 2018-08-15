package io.github.ititus.mymod.network.message;

import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.network.NetworkState;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageContainerUpdate implements IMessage, IMessageHandler<MessageContainerUpdate, IMessage> {

    private BlockPos pos;
    private NetworkState state;

    public MessageContainerUpdate() {
    }

    public MessageContainerUpdate(BlockPos pos, NetworkState state) {
        this.pos = pos;
        this.state = state;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.state = NetworkState.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        NetworkState.toBytes(buf, state);
    }

    @Override
    public IMessage onMessage(MessageContainerUpdate msg, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            TileEntity tile = FMLClientHandler.instance().getWorldClient().getTileEntity(msg.pos);
            if (tile instanceof TileBase) {
                ((TileBase) tile).loadFromNetworkState(msg.state);
            }
        });
        return null;
    }
}

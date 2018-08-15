package io.github.ititus.mymod.network.message;

import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.network.NetworkState;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiUpdate implements IMessage, IMessageHandler<MessageGuiUpdate, IMessage> {

    private BlockPos pos;
    private NetworkState state;

    public MessageGuiUpdate() {
    }

    public MessageGuiUpdate(BlockPos pos, NetworkState state) {
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
    public IMessage onMessage(MessageGuiUpdate msg, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
            TileEntity tile = ctx.getServerHandler().player.world.getTileEntity(msg.pos);
            if (tile instanceof TileBase) {
                ((TileBase) tile).loadFromGuiState(msg.state);
            }
        });
        return null;
    }
}

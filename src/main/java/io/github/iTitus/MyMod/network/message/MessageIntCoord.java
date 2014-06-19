package io.github.iTitus.MyMod.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public abstract class MessageIntCoord<REQ extends IMessage> implements
		IMessage, IMessageHandler<REQ, IMessage> {

	protected int x, y, z;

	public MessageIntCoord() {
	}

	public MessageIntCoord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	public NetworkRegistry.TargetPoint getTargetPoint(World world) {
		return getTargetPoint(world, 64);
	}

	public NetworkRegistry.TargetPoint getTargetPoint(World world,
			double updateDistance) {
		return new NetworkRegistry.TargetPoint(world.provider.dimensionId, x,
				y, z, updateDistance);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

}

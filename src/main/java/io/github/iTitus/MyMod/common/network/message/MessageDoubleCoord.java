package io.github.iTitus.MyMod.common.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public abstract class MessageDoubleCoord<REQ extends IMessage> implements
		IMessage, IMessageHandler<REQ, IMessage> {

	protected double x, y, z;

	public MessageDoubleCoord() {
	}

	public MessageDoubleCoord(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	public NetworkRegistry.TargetPoint getTargetPoint(World world) {
		return getTargetPoint(world, 64);
	}

	public NetworkRegistry.TargetPoint getTargetPoint(World world,
			double updateDistance) {
		return new NetworkRegistry.TargetPoint(world.provider.dimensionId, x,
				y, z, updateDistance);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}
}

package io.github.iTitus.MyMod.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public abstract class IntCoordMessage<REQ extends IMessage> extends
		AbstractMessage<REQ> {

	protected int x, y, z;

	public IntCoordMessage() {
	}

	public IntCoordMessage(int x, int y, int z) {
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

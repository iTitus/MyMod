package io.github.iTitus.MyMod.network.message;

import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMyTileEntity extends MessageIntCoord<MessageMyTileEntity> {

	protected String customName, owner;
	protected byte orientation, state;

	public MessageMyTileEntity() {
		super();
	}

	public MessageMyTileEntity(MyTileEntity tile) {
		super(tile.xCoord, tile.yCoord, tile.zCoord);
		customName = tile.getCustomName();
		owner = tile.getOwner();
		orientation = (byte) tile.getOrientation().ordinal();
		state = tile.getState();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		orientation = buf.readByte();
		state = buf.readByte();
		int customNameLength = buf.readInt();
		customName = new String(buf.readBytes(customNameLength).array());
		int ownerLength = buf.readInt();
		owner = new String(buf.readBytes(ownerLength).array());

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeByte(orientation);
		buf.writeByte(state);
		buf.writeInt(customName.length());
		buf.writeBytes(customName.getBytes());
		buf.writeInt(owner.length());
		buf.writeBytes(owner.getBytes());

	}

	@Override
	public IMessage onMessage(MessageMyTileEntity message, MessageContext ctx) {

		MyTileEntity tile = (MyTileEntity) Minecraft.getMinecraft().theWorld
				.getTileEntity(message.x, message.y, message.z);

		tile.setOrientation(message.orientation);
		tile.setState(message.state);
		tile.setCustomName(message.customName);
		tile.setOwner(message.owner);

		return null;
	}

}

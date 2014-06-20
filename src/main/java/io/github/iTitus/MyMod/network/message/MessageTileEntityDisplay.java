package io.github.iTitus.MyMod.network.message;

import io.github.iTitus.MyMod.tileentity.display.TileEntityDisplay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntityDisplay extends
		MessageIntCoord<MessageTileEntityDisplay> {

	private String[] text;
	protected String customName, owner;
	protected byte orientation, state;

	public MessageTileEntityDisplay() {
		super();
	}

	public MessageTileEntityDisplay(TileEntityDisplay tile) {
		super(tile.xCoord, tile.yCoord, tile.zCoord);
		customName = tile.getCustomName();
		owner = tile.getOwner();
		orientation = (byte) tile.getOrientation().ordinal();
		state = tile.getState();
		this.text = tile.getText();
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
		text = new String[buf.readInt()];
		for (int i = 0; i < text.length; i++) {
			int length = buf.readInt();
			text[i] = new String(buf.readBytes(length).array());
		}

	}

	@Override
	public IMessage onMessage(MessageTileEntityDisplay message,
			MessageContext ctx) {

		TileEntityDisplay tile = (TileEntityDisplay) Minecraft.getMinecraft().theWorld
				.getTileEntity(message.x, message.y, message.z);

		tile.setOrientation(message.orientation);
		tile.setState(state);
		tile.setCustomName(message.customName);
		tile.setOwner(message.owner);

		tile.setText(text);

		return null;
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
		buf.writeInt(text.length);
		for (String str : text) {
			buf.writeInt(str.length());
			buf.writeBytes(str.getBytes());
		}
	}

}

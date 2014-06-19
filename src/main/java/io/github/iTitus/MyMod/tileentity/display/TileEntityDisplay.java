package io.github.iTitus.MyMod.tileentity.display;

import io.github.iTitus.MyMod.network.NetworkHandler;
import io.github.iTitus.MyMod.network.message.MessageTileEntityDisplay;
import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityDisplay extends MyTileEntity {

	// TODO:
	private String[] text;

	public TileEntityDisplay() {
		super();
		// TODO:
		text = new String[0];
	}

	public boolean isActive() {
		return state != 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

	}

	@Override
	public Packet getDescriptionPacket() {
		return NetworkHandler.INSTANCE
				.getPacketFrom(new MessageTileEntityDisplay(this));
	}

	public String[] getText() {
		// TODO:
		return new String[0];
	}

	public void setText(String[] text) {
		// TODO:
	}

}

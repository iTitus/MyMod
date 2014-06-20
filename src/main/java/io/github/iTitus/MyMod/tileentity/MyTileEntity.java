package io.github.iTitus.MyMod.tileentity;

import io.github.iTitus.MyMod.network.NetworkHandler;
import io.github.iTitus.MyMod.network.message.MessageMyTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class MyTileEntity extends TileEntity {

	private static final String TAG_ORIENTATION = "orientation",
			TAG_NAME = "customName";

	protected String customName, owner;
	protected ForgeDirection orientation;
	protected byte state;

	public MyTileEntity() {
		customName = owner = "";
		orientation = ForgeDirection.UP;
		state = 0;
	}

	public String getCustomName() {
		return customName;
	}

	@Override
	public Packet getDescriptionPacket() {
		return NetworkHandler.INSTANCE.getPacketFrom(new MessageMyTileEntity(
				this));
	}

	public ForgeDirection getOrientation() {
		return orientation;
	}

	public String getOwner() {
		return owner;
	}

	public byte getState() {
		return state;
	}

	public boolean hasCustomName() {
		return customName != null && customName.length() > 0;
	}

	public boolean hasOwner() {
		return owner != null && owner.length() > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		orientation = ForgeDirection.getOrientation(nbtTagCompound
				.getByte(TAG_ORIENTATION));
		customName = nbtTagCompound.getString(TAG_NAME);

	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public void setOrientation(ForgeDirection orientation) {
		this.orientation = orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = ForgeDirection.getOrientation(orientation);
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setState(byte state) {
		this.state = state;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setByte(TAG_ORIENTATION, (byte) orientation.ordinal());
		if (this.hasCustomName()) {
			nbtTagCompound.setString(TAG_NAME, customName);
		}
	}

}
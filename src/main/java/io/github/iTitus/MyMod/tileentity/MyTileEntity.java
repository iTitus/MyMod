package io.github.iTitus.MyMod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class MyTileEntity extends TileEntity {

	private static final String TAG_ORIENTATION = "orientation",
			TAG_NAME = "customName", TAG_OWNER = "owner";

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
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
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
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		orientation = ForgeDirection.getOrientation(nbt
				.getByte(TAG_ORIENTATION));
		customName = nbt.getString(TAG_NAME);
		owner = nbt.getString(TAG_OWNER);

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
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte(TAG_ORIENTATION, (byte) orientation.ordinal());
		if (hasCustomName()) {
			nbt.setString(TAG_NAME, customName);
		}
		nbt.setString(TAG_OWNER, owner);
	}

}
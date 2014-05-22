package com.iTitus.MyMod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class MyTileEntity extends TileEntity {

	public static final String TAG_ORIENTATION = "orientation",
			TAG_NAME = "customName";

	protected String customName;
	private ForgeDirection orientation;

	public MyTileEntity() {
		orientation = ForgeDirection.SOUTH;
		customName = "";
	}

	public String getCustomName() {
		return customName;
	}

	public ForgeDirection getOrientation() {
		return orientation;
	}

	public boolean hasCustomName() {
		return customName != null && customName.length() > 0;
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

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setByte(TAG_ORIENTATION, (byte) orientation.ordinal());
		if (this.hasCustomName()) {
			nbtTagCompound.setString(TAG_NAME, customName);
		}
	}

}
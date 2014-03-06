package com.iTitus.MyMod.tileentiy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class MyTileEntity extends TileEntity {

	public static final String TAG_ORIENTATION = "orientation",
			TAG_NAME = "customName";

	private ForgeDirection orientation;
	protected String customName;

	public MyTileEntity() {
		orientation = ForgeDirection.SOUTH;
		customName = "";
	}

	public ForgeDirection getOrientation() {
		return orientation;
	}

	public void setOrientation(ForgeDirection orientation) {
		this.orientation = orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = ForgeDirection.getOrientation(orientation);
	}

	public boolean hasCustomName() {
		return customName != null && customName.length() > 0;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		orientation = ForgeDirection.getOrientation(nbtTagCompound
				.getByte(TAG_ORIENTATION));
		customName = nbtTagCompound.getString(TAG_NAME);

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

package io.github.iTitus.MyMod.tileentity.display;

import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDisplay extends MyTileEntity {

	// TODO:
	private String[] text;

	public TileEntityDisplay() {
		super();
		// TODO:
		text = new String[0];
	}

	public String[] getText() {
		// TODO:
		return new String[0];
	}

	public boolean isActive() {
		return state == 1;
	}

	public boolean isAttached() {
		return getBlockMetadata() == 0;
	}

	public boolean isHanging() {
		return getBlockMetadata() == 2;
	}

	public boolean isStanding() {
		return getBlockMetadata() == 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}

	public void setActive(boolean active) {
		state = (byte) (active ? 1 : 0);
	}

	public void setAttachingMode(int mode) {
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, mode, 3);
	}

	public void setText(String[] text) {
		// TODO:
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

}

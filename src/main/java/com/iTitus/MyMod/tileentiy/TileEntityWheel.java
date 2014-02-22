package com.iTitus.MyMod.tileentiy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWheel extends TileEntity {

	private double deg, velo, acc;

	private static final double friction = -0.1;

	private static final String TAG_DEG = "degrees", TAG_VELO = "velocity",
			TAG_ACC = "acceleration";

	public TileEntityWheel() {
		super();
		deg = 0;
		velo = 0;
		acc = 0;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		acc += friction;
		if (acc < friction)
			acc = friction;

		velo += acc;
		if (velo < 0)
			velo = 0;

		deg += velo;
		if (deg >= 360) {
			do {
				deg -= 360;
			} while (deg >= 360);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble(TAG_DEG, deg);
		nbt.setDouble(TAG_VELO, velo);
		nbt.setDouble(TAG_ACC, acc);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		deg = nbt.getDouble(TAG_DEG);
		velo = nbt.getDouble(TAG_VELO);
		acc = nbt.getDouble(TAG_ACC);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public float getRotationAngleRad() {
		float f = (float) ((deg / 360D) * (2D * Math.PI));
		System.out.println(deg + " -> " + f);
		return f;
	}

	public boolean onBlockActivated(EntityPlayer p, int side, float hitX,
			float hitY, float hitZ) {

		if (p.getHeldItem() == null && velo == 0
				&& worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0) {
			acc = (Math.random() * 1D) + 1D;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
		}

		return true;

	}
}

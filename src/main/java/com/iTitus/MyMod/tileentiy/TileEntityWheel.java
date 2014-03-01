package com.iTitus.MyMod.tileentiy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants.NBT;

import com.iTitus.MyMod.helper.ItemHelper;
import com.iTitus.MyMod.network.PacketPipeline;
import com.iTitus.MyMod.network.PacketWheel;

public class TileEntityWheel extends MyTileEntity implements IInventory {

	private double deg, velo, acc;
	private boolean running;

	private ItemStack[] inv;

	private static final double FRICTION = -0.1;

	private static final String TAG_DEG = "degrees", TAG_VELO = "velocity",
			TAG_ACC = "acceleration", TAG_RUNNING = "running";

	public TileEntityWheel() {
		super();
		deg = 0;
		velo = 0;
		acc = 0;
		running = false;
		inv = new ItemStack[3];
	}

	@Override
	public void updateEntity() {
		System.out.println(getBlockMetadata());

		acc += FRICTION;
		if (acc < FRICTION)
			acc = FRICTION;

		velo += acc;
		if (velo < 0)
			velo = 0;

		deg += velo;
		if (deg >= 360) {
			do {
				deg -= 360;
			} while (deg >= 360);
		}

		if (getBlockMetadata() == 15) {
			PacketPipeline.INSTANCE.sendToDimension(new PacketWheel(xCoord,
					yCoord, zCoord, acc, velo, deg),
					worldObj.provider.dimensionId);
		}

		if (acc == FRICTION && velo == 0 && getBlockMetadata() == 15 && running) {
			System.out.println("Stopped! - " + acc + " - " + velo + " - " + deg
					+ " - " + getBlockMetadata());
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 8, 3);
		}

		if (getBlockMetadata() == 8) {
			running = false;
			WeightedRandomChestContent.generateChestContents(worldObj.rand,
					ChestGenHooks.getItems(ChestGenHooks.DUNGEON_CHEST,
							worldObj.rand), this, ChestGenHooks.getCount(
							ChestGenHooks.DUNGEON_CHEST, worldObj.rand));
			// TODO: Choose item based on degrees
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 9, 3);
		}

		if (getBlockMetadata() == 9) {
			// TODO: Fancy win thingie!
			ItemHelper.dropInventory(worldObj, xCoord, yCoord, zCoord);
			worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0, true);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble(TAG_DEG, deg);
		nbt.setDouble(TAG_VELO, velo);
		nbt.setDouble(TAG_ACC, acc);
		nbt.setBoolean(TAG_RUNNING, running);

		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				tagList.appendTag(tag);
			}
		}
		nbt.setTag("Items", tagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		deg = nbt.getDouble(TAG_DEG);
		velo = nbt.getDouble(TAG_VELO);
		acc = nbt.getDouble(TAG_ACC);
		running = nbt.getBoolean(TAG_RUNNING);

		NBTTagList tagList = nbt.getTagList("Items", NBT.TAG_COMPOUND);
		inv = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot < getSizeInventory() && slot >= 0) {
				setInventorySlotContents(slot,
						ItemStack.loadItemStackFromNBT(tag));
			}
		}
	}

	public float getRotationAngleRad() {
		return (float) ((deg / 360D) * (2D * Math.PI));
	}

	public boolean onBlockActivated(EntityPlayer p, int side, float hitX,
			float hitY, float hitZ) {

		if (p.getHeldItem() == null && velo == 0
				&& worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0) {
			acc = (Math.random() * 3D) + 1D;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 15, 3);
			running = true;
		}

		return true;

	}

	public void setAcc(double acc) {
		this.acc = acc;
	}

	public void setVelo(double velo) {
		this.velo = velo;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return (slot < getSizeInventory() && slot >= 0) ? inv[slot] : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
			return stack;
		}
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot < getSizeInventory() && slot >= 0)
			inv[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? getCustomName() : "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return hasCustomName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

}

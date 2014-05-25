package io.github.iTitus.MyMod.tileentity.wheel;

import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.helper.InventoryHelper;
import io.github.iTitus.MyMod.lib.LibGUI;
import io.github.iTitus.MyMod.network.PacketPipeline;
import io.github.iTitus.MyMod.network.PacketWheel;
import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityWheel extends MyTileEntity implements ISidedInventory {

	public enum Mode {
		BLOCKED, CHOOSING_ITEMS, CUSTOM_FILLED_CHOOSING_ITEMS, CUSTOM_FILLED_RUNNING, EMPTY, FILLED, FINISHED_CHOOSING, IDLE_CUSTOM, RUNNING;

		public static Mode getModeForOrdinal(int ordinal) {
			return (ordinal < values().length) ? (values()[ordinal]) : (null);
		}
	}

	private static final double FRICTION = -0.1;

	private static final String TAG_DEG = "degrees", TAG_VELO = "velocity",
			TAG_ACC = "acceleration", TAG_RUNNING = "running",
			TAG_MODE = "mode";
	private double deg, velo, acc;

	private ItemStack[] inv;

	private Mode mode;

	public TileEntityWheel() {
		super();
		deg = 0;
		velo = 0;
		acc = 0;
		mode = Mode.EMPTY;
		inv = new ItemStack[3];
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return false;
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return false;
	}

	@Override
	public void closeInventory() {
		if (InventoryHelper.isInventoryEmpty(this)) {
			setMode(Mode.IDLE_CUSTOM);
		} else {
			setMode(Mode.EMPTY);
		}
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
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] {};
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? getCustomName() : "";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public Mode getMode() {
		return mode;
	}

	public float getRotationAngleRad() {
		return (float) ((deg / 360D) * (2D * Math.PI));
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
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
			return stack;
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return hasCustomName();
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
	}

	public boolean onBlockActivated(EntityPlayer p, int side, float hitX,
			float hitY, float hitZ) {

		if (p.getHeldItem() == null && velo == 0) {
			if (mode == Mode.FILLED) {
				acc = (Math.random() * 3D) + 1D;
				setMode(Mode.RUNNING);
			} else if (mode == Mode.IDLE_CUSTOM) {
				acc = (Math.random() * 3D) + 1D;
				setMode(Mode.CUSTOM_FILLED_RUNNING);
			}
		}

		if (!p.isSneaking() && mode == Mode.EMPTY) {
			p.openGui(MyMod.instance, LibGUI.WHEEL_GUI_ID, worldObj, xCoord,
					yCoord, zCoord);
		}

		return true;

	}

	@Override
	public void openInventory() {
		setMode(Mode.BLOCKED);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		deg = nbt.getDouble(TAG_DEG);
		velo = nbt.getDouble(TAG_VELO);
		acc = nbt.getDouble(TAG_ACC);
		mode = Mode.getModeForOrdinal(nbt.getInteger(TAG_MODE));

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

	public void setAcc(double acc) {
		this.acc = acc;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot < getSizeInventory() && slot >= 0)
			inv[slot] = stack;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		PacketPipeline.INSTANCE.sendToDimension(new PacketWheel(xCoord, yCoord,
				zCoord, acc, velo, deg, mode.ordinal()),
				worldObj.provider.dimensionId);
	}

	public void setVelo(double velo) {
		this.velo = velo;
	}

	@Override
	public void updateEntity() {

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

		if (mode == Mode.CUSTOM_FILLED_RUNNING || mode == Mode.RUNNING) {
			PacketPipeline.INSTANCE.sendToDimension(new PacketWheel(xCoord,
					yCoord, zCoord, acc, velo, deg, mode.ordinal()),
					worldObj.provider.dimensionId);
		}

		if (acc == FRICTION && velo == 0 && mode == Mode.CUSTOM_FILLED_RUNNING) {
			setMode(Mode.CUSTOM_FILLED_CHOOSING_ITEMS);
		}

		if (acc == FRICTION && velo == 0 && mode == Mode.RUNNING) {
			setMode(Mode.CHOOSING_ITEMS);
		}

		if (mode == Mode.CUSTOM_FILLED_CHOOSING_ITEMS) {
			// TODO: Choose item based on degrees
			setMode(Mode.FINISHED_CHOOSING);
		}

		if (mode == Mode.CHOOSING_ITEMS) {
			WeightedRandomChestContent.generateChestContents(worldObj.rand,
					ChestGenHooks.getItems(ChestGenHooks.DUNGEON_CHEST,
							worldObj.rand), this, ChestGenHooks.getCount(
							ChestGenHooks.DUNGEON_CHEST, worldObj.rand));
			// TODO: Choose item based on degrees
			setMode(Mode.FINISHED_CHOOSING);
		}

		if (mode == Mode.FINISHED_CHOOSING) {
			// TODO: Fancy win thingie!
			InventoryHelper.dropInventory(worldObj, xCoord, yCoord, zCoord);
			worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0, true);
			setMode(Mode.EMPTY);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble(TAG_DEG, deg);
		nbt.setDouble(TAG_VELO, velo);
		nbt.setDouble(TAG_ACC, acc);
		nbt.setInteger(TAG_MODE, mode.ordinal());

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

}

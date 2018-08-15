package io.github.ititus.mymod.tile;

import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.fluid.FluidTankBase;
import io.github.ititus.mymod.util.network.NetworkState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileTank extends TileBase implements ITickable {

    public static final int CAPACITY = 16_000;
    private static final int MAX_TRANSFER = 1000;
    private static final int SLOTS = 2, SLOT_INPUT = 0, SLOT_OUTPUT = 1;
    private static final int[] INPUT_SLOTS = {SLOT_INPUT, SLOT_OUTPUT}, OUTPUT_SLOTS = {SLOT_INPUT, SLOT_OUTPUT};

    public final FluidTankBase tank = new FluidTankBase(this, CAPACITY);
    public final ItemStackHandlerBase inventory = new ItemStackHandlerBase(SLOTS, this) {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean canInsert(ItemStack stack, int slot) {
            return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        }
    };

    public TileTank() {
        super(ModBlocks.tank.getRegistryName().toString());
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return super.shouldRenderInPass(pass);
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }
        drainItem();
        fillItem();
    }

    private void drainItem() {
        if (tank.getFreeSpace() <= 0) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                handler.drain(tank.fill(handler.drain(MAX_TRANSFER, false), true), true);
                inventory.setStackInSlot(SLOT_INPUT, handler.getContainer());
            }
        }
    }

    private void fillItem() {
        if (tank.getFluidAmount() <= 0) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                tank.drain(handler.fill(tank.drain(MAX_TRANSFER, false), true), true);
                inventory.setStackInSlot(SLOT_OUTPUT, handler.getContainer());
            }
        }
    }

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public int getComparatorLevel() {
        return MathUtil.scaledClamp(tank.getFluidAmount(), tank.getCapacity(), 15);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("tank")) {
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(tank, null, compound.getTag("tank"));
        }
        if (compound.hasKey("inventory")) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound.getTag("inventory"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("tank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(tank, null));
        compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        return compound;
    }

    @Override
    public ItemStackHandlerBase getInventory() {
        return inventory;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) tank : super.getCapability(capability, facing);
    }

    @Override
    public NetworkState getNetworkState() {
        return new NetworkState(0, 1, 1);
    }

    @Override
    public void updateNetworkState(NetworkState state) {
        super.updateNetworkState(state);
        FluidStack fluid = tank.getFluid();
        state.set(0, fluid != null ? fluid.copy() : null);
    }

    @Override
    public void loadFromNetworkState(NetworkState state) {
        super.loadFromNetworkState(state);
        FluidStack fluid = state.getFluidStack(0);
        tank.setFluid(fluid != null ? fluid.copy() : null);
    }


    public int getFluidToDrain() {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                FluidStack fluidStack = prop.getContents();
                return prop.getCapacity() - (fluidStack != null ? fluidStack.amount : 0);
            }
        }
        return 0;
    }

    public int getCapacityToDrain() {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                return prop.getCapacity();
            }
        }
        return 0;
    }

    public int getFluidToFill() {
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                FluidStack fluidStack = prop.getContents();
                return fluidStack != null ? fluidStack.amount : 0;
            }
        }
        return 0;
    }

    public int getCapacityToFill() {
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                return prop.getCapacity();
            }
        }
        return 0;
    }
}

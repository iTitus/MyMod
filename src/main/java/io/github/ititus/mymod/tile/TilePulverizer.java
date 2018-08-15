package io.github.ititus.mymod.tile;

import io.github.ititus.mymod.api.recipe.RecipeManager;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyAcceptor;
import io.github.ititus.mymod.util.network.NetworkState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class TilePulverizer extends TileBase implements ITickable {

    public static final int CAPACITY = 10_000;
    public static final int FE_PER_TICK = 20;
    private static final int SLOTS = 3, SLOT_INPUT = 0, SLOT_OUTPUT_1 = 1, SLOT_OUTPUT_2 = 2;
    private static final int[] INPUT_SLOTS = {SLOT_INPUT}, OUTPUT_SLOTS = {SLOT_OUTPUT_1, SLOT_OUTPUT_2};

    public final EnergyAcceptor energy = new EnergyAcceptor(CAPACITY);
    public final ItemStackHandlerBase inventory = new ItemStackHandlerBase(SLOTS, this) {

        @Override
        public boolean canInsert(ItemStack stack, int slot) {
            return slot == SLOT_INPUT && RecipeManager.pulverizer.get(stack) != null;
        }

        @Override
        public boolean canExtract(int slot) {
            return slot != SLOT_INPUT;
        }

        @Override
        public boolean canExtractFromSlot(int slot) {
            return true;
        }
    };
    public IPulverizerRecipe recipe;
    public int progress, maxProgress;
    private int energyLastTick, progressLastTick;


    public TilePulverizer() {
        super(ModBlocks.pulverizer.getRegistryName().toString());
    }

    @Override
    public void update() {
        if (energy.getEnergyStored() != energyLastTick || progress != progressLastTick) {
            energyLastTick = energy.getEnergyStored();
            progressLastTick = progress;
            markDirty();
        }

        if (world.isRemote) {
            return;
        }
        process();
    }

    private void process() {
        checkRecipe();
        if (canProcess()) {
            energy.extractEnergyInternal(FE_PER_TICK, false);
            if (++progress >= maxProgress) {
                finishProcess();
            }
            // markDirty();
        }
    }

    private void checkRecipe() {
        IPulverizerRecipe recipe = RecipeManager.pulverizer.get(inventory.getStackInSlot(SLOT_INPUT));
        if (recipe != null) {
            if (this.recipe == null) {
                this.recipe = recipe;
                maxProgress = recipe.getTime();
            } else {
                if (this.recipe != recipe) {
                    this.recipe = recipe;
                    maxProgress = recipe.getTime();
                    progress = 0;
                }
            }
        } else {
            this.recipe = null;
            maxProgress = 0;
            progress = 0;
        }
    }

    private boolean canProcess() {
        if (recipe == null) {
            return false;
        }
        if (energy.extractEnergyInternal(FE_PER_TICK, true) < FE_PER_TICK) {
            return false;
        }
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.isEmpty() || stack.getCount() < recipe.getInput().getAmount()) {
            return false;
        }
        ItemStack stack1 = recipe.getPrimaryOutput().getExample();
        ItemStack stack2 = recipe.getSecondaryOutput() != null ? recipe.getSecondaryOutput().getExample() : ItemStack.EMPTY;
        if (stack2.isEmpty()) {
            ItemStack remainder = inventory.insertItemInternal(SLOT_OUTPUT_1, stack1, true);
            if (!remainder.isEmpty()) {
                if (!inventory.insertItemInternal(SLOT_OUTPUT_2, remainder, true).isEmpty()) {
                    return false;
                }
            }
        } else {
            if (!inventory.insertItemInternal(SLOT_OUTPUT_1, stack1, true).isEmpty()) {
                return false;
            }
            if (!inventory.insertItemInternal(SLOT_OUTPUT_2, stack2, true).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void finishProcess() {
        inventory.extractItemInternal(SLOT_INPUT, recipe.getInput().getAmount(), false);
        ItemStack remainder = inventory.insertItemInternal(SLOT_OUTPUT_1, recipe.getPrimaryOutput().getOutput(world.rand), false);
        if (recipe.getSecondaryOutput() != null) {
            inventory.insertItemInternal(SLOT_OUTPUT_2, recipe.getSecondaryOutput().getOutput(world.rand), false);
        } else {
            if (!remainder.isEmpty()) {
                inventory.insertItemInternal(SLOT_OUTPUT_2, remainder, false);
            }
        }
        progress = 0;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("energy")) {
            CapabilityEnergy.ENERGY.readNBT(energy, null, compound.getTag("energy"));
        }
        if (compound.hasKey("inventory")) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound.getTag("inventory"));
        }
        progress = compound.getInteger("progress");
        checkRecipe();
        energyLastTick = energy.getEnergyStored();
        progressLastTick = progress;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(energy, null));
        compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        compound.setInteger("progress", progress);
        return compound;
    }

    @Override
    public EnergyAcceptor getEnergy() {
        return energy;
    }

    @Override
    public ItemStackHandlerBase getInventory() {
        return inventory;
    }

    @Override
    public int getComparatorLevel() {
        return MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), 15);
    }

    @Override
    public NetworkState getNetworkState() {
        return new NetworkState(3, 0, 1);
    }

    @Override
    public void updateNetworkState(NetworkState state) {
        super.updateNetworkState(state);
        state.set(0, energy.getEnergyStored());
        state.set(1, progress);
        state.set(2, maxProgress);
    }

    @Override
    public void loadFromNetworkState(NetworkState state) {
        super.loadFromNetworkState(state);
        energy.setEnergyStored(state.getInt(0));
        progress = state.getInt(1);
        maxProgress = state.getInt(2);
    }
}

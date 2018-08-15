package io.github.ititus.mymod.tile;

import com.google.common.collect.Sets;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import io.github.ititus.mymod.util.grid.IGridMember;
import io.github.ititus.mymod.util.multi.energy.EnergyCellGrid;
import io.github.ititus.mymod.util.network.NetworkState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TileEnergyCell extends TileBase implements ITickable, IGridMember<EnergyCellGrid> {

    public static final int CAPACITY = 100_000;
    public static final int MAX_TRANSFER = 1_000;

    private static final int SLOTS = 4, SLOT_DISCHARGE_IN = 0, SLOT_DISCHARGE_OUT = 1, SLOT_CHARGE_IN = 2, SLOT_CHARGE_OUT = 3;
    private static final int[] INPUT_SLOTS = {SLOT_DISCHARGE_IN, SLOT_CHARGE_IN}, OUTPUT_SLOTS = {SLOT_DISCHARGE_OUT, SLOT_CHARGE_OUT};

    public final ItemStackHandlerBase inventory = new ItemStackHandlerBase(SLOTS, this) {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean canInsert(ItemStack stack, int slot) {
            return (slot == SLOT_DISCHARGE_IN || slot == SLOT_CHARGE_IN) && stack.hasCapability(CapabilityEnergy.ENERGY, null);
        }

        @Override
        public boolean canExtract(int slot) {
            return slot == SLOT_DISCHARGE_OUT || slot == SLOT_CHARGE_OUT;
        }

        @Override
        public boolean canExtractFromSlot(int slot) {
            return true;
        }
    };
    private final EnergyStorageBase energy = new EnergyStorageBase(CAPACITY, MAX_TRANSFER);
    public int energyLevel = 0;
    private int energyLastTick;
    private EnergyCellGrid grid;

    public TileEnergyCell() {
        super(ModBlocks.energyCell.getRegistryName().toString());
    }

    @Override
    public void update() {
        IGridMember.super.update();

        EnergyStorageBase energy = getEnergy();
        int newEnergyLevel = MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), 10);
        if (newEnergyLevel != energyLevel) {
            energyLevel = newEnergyLevel;
            sync();
        }

        energy = getEnergyStorageBase();
        if (energy.getEnergyStored() != energyLastTick) {
            energyLastTick = energy.getEnergyStored();
            markDirty();
        }

        if (world.isRemote) {
            return;
        }
        dischargeItem();
        chargeItem();
        //equalizeEnergy();
    }

    public void checkGrids() {
        Set<EnergyCellGrid> grids = Arrays.stream(EnumFacing.values())
                .map(facing -> pos.offset(facing))
                .map(pos -> world.getTileEntity(pos))
                .filter(tile -> tile instanceof TileEnergyCell && !tile.isInvalid() && tile.getBlockMetadata() == 0)
                .map(tile -> (TileEnergyCell) tile)
                .map(TileEnergyCell::getGrid)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (grids.isEmpty()) {
            grid = new EnergyCellGrid();
            grid.addMember(this);
        } else {
            Iterator<EnergyCellGrid> iterator = grids.iterator();
            EnergyCellGrid firstGrid = iterator.next();

            firstGrid.addMember(this);

            while (iterator.hasNext()) {
                EnergyCellGrid grid = iterator.next();
                firstGrid.merge(grid);
            }
        }
    }

    @Override
    public EnergyCellGrid getGrid() {
        return grid;
    }

    @Override
    public void setGrid(EnergyCellGrid grid) {
        this.grid = grid;
    }


    @Override
    public boolean valid() {
        return !isInvalid() && getBlockMetadata() == 0;
    }

    @Override
    public long toLong() {
        if (valid()) {
            return pos.toLong();
        }
        return 0;
    }

    public void showGridParticles() {
        if (grid == null) {
            world.addBlockEvent(pos, world.getBlockState(pos).getBlock(), 2, 0);
        } else {
            Set<TileEnergyCell> pipes = grid.getMembers();
            Set<BlockPos> gridPosSet = Sets.newHashSet();

            Iterator<TileEnergyCell> iterator = pipes.iterator();
            while (iterator.hasNext()) {
                TileEnergyCell energyCell = iterator.next();
                if (energyCell != null && !energyCell.isInvalid() && energyCell.getBlockMetadata() == 0) {
                    gridPosSet.add(energyCell.getPos());
                }
            }

            for (BlockPos pos : gridPosSet) {
                world.addBlockEvent(pos, world.getBlockState(pos).getBlock(), 3, grid.hashCode());
            }

        }
    }

    private void dischargeItem() {
        EnergyStorageBase energy = getEnergy();
        ItemStack stack = inventory.getStackInSlot(SLOT_DISCHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                if (energy.getFreeSpace() > 0) {
                    storage.extractEnergy(energy.receiveEnergy(storage.extractEnergy(Math.min(energy.getFreeSpace(), MAX_TRANSFER), true), false), false);
                }
                if (storage.getEnergyStored() <= 0) {
                    ItemStack rem = inventory.insertItemInternal(SLOT_DISCHARGE_OUT, stack, false);
                    if (rem.isEmpty()) {
                        inventory.setStackInSlot(SLOT_DISCHARGE_IN, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    private void chargeItem() {
        IEnergyStorage energy = getEnergy();
        ItemStack stack = inventory.getStackInSlot(SLOT_CHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                if (energy.getEnergyStored() > 0) {
                    energy.extractEnergy(storage.receiveEnergy(energy.extractEnergy(MAX_TRANSFER, true), false), false);
                }
                if (storage.getEnergyStored() >= storage.getMaxEnergyStored()) {
                    ItemStack rem = inventory.insertItemInternal(SLOT_CHARGE_OUT, stack, false);
                    if (rem.isEmpty()) {
                        inventory.setStackInSlot(SLOT_CHARGE_IN, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    private void equalizeEnergy() {
        EnergyStorageBase energy = getEnergy();
        Set<EnergyStorageBase> cells = Sets.newHashSet(energy);
        Set<EnergyStorageBase> toExtract = Sets.newHashSet(energy);
        Set<EnergyStorageBase> receivers = Sets.newHashSet(energy);

        for (EnumFacing facing : EnumFacing.VALUES) {
            TileEntity tile = world.getTileEntity(pos.offset(facing));
            if (tile instanceof TileEnergyCell && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                if (storage instanceof EnergyStorageBase) {
                    EnergyStorageBase storageBase = (EnergyStorageBase) storage;
                    if (storageBase.canExtract()) {
                        toExtract.add(storageBase);
                    }
                    if (storageBase.canReceive()) {
                        receivers.add(storageBase);
                    }
                    cells.add(storageBase);
                }
            }
        }

        if (cells.size() > 1) {
            int totalEnergyToExtract = toExtract.stream().mapToInt(EnergyStorageBase::getEnergyStored).sum();
            if (totalEnergyToExtract >= receivers.size()) {
                int distributed = cells.stream().mapToInt(EnergyStorageBase::getEnergyStored).sum() / cells.size();

                Iterator<EnergyStorageBase> it;
                for (it = toExtract.iterator(); it.hasNext(); ) {
                    EnergyStorageBase storage = it.next();
                    int stored = storage.getEnergyStored();
                    if (stored < distributed) {
                        it.remove();
                    }
                }
                for (it = receivers.iterator(); it.hasNext(); ) {
                    EnergyStorageBase storage = it.next();
                    int stored = storage.getEnergyStored();
                    if (stored > distributed) {
                        it.remove();
                    }
                }

                int extracted = 0;
                for (EnergyStorageBase storage : toExtract) {
                    extracted += storage.extractEnergy(storage.getEnergyStored() - distributed, false);
                }

                int i = 0;
                for (EnergyStorageBase storage : receivers) {
                    extracted -= storage.receiveEnergy(Math.min(distributed - storage.getEnergyStored(), extracted / (receivers.size() - i)), false);
                    i++;
                }

                receivers.remove(energy);
                if (extracted > 0) {
                    int maxTransfer = MathHelper.ceil(extracted / (double) receivers.size());
                    for (EnergyStorageBase storage : receivers) {
                        extracted -= storage.receiveEnergyInternal(Math.min(extracted, maxTransfer), false);
                        if (extracted <= 0) {
                            break;
                        }
                    }
                    if (extracted > 0) {
                        energy.receiveEnergyInternal(extracted, false);
                    }
                }
            }
        }
    }

    @Override
    public int getComparatorLevel() {
        EnergyStorageBase energy = getEnergy();
        return MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), 15);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //EnergyStorageBase energy = getEnergy();
        if (compound.hasKey("energy")) {
            CapabilityEnergy.ENERGY.readNBT(energy, null, compound.getTag("energy"));
        }
        if (compound.hasKey("inventory")) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound.getTag("inventory"));
        }
        energyLastTick = energy.getEnergyStored();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        if (getBlockMetadata() == 0) {
            compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(energy, null));
        }
        compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        return compound;
    }

    @Override
    public ItemStackHandlerBase getInventory() {
        return inventory;
    }

    @Override
    public NetworkState getNetworkState() {
        return new NetworkState(1, 0, 1);
    }

    @Override
    public void updateNetworkState(NetworkState state) {
        super.updateNetworkState(state);
        EnergyStorageBase energy = getEnergyStorageBase();
        state.set(0, energy.getEnergyStored());
    }

    @Override
    public void loadFromNetworkState(NetworkState state) {
        super.loadFromNetworkState(state);
        EnergyStorageBase energy = getEnergyStorageBase();
        energy.setEnergyStored(state.getInt(0));
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
    public boolean receiveClientEvent(int id, int type) {
        if (world.isRemote) {
            if (id == 2) {
                for (int i = 0; i < 8; i++) {
                    world.spawnParticle(EnumParticleTypes.BARRIER, pos.getX() + 0.75 - world.rand.nextDouble() * 0.5, pos.getY() + 0.5 + 0.75 - world.rand.nextDouble() * 0.5, pos.getZ() + 0.75 - world.rand.nextDouble() * 0.5, 0, 0.1, 0);
                }
            } else if (id == 3) {
                for (int i = 0; i < 8; i++) {
                    int r = (type >> 16) & 0xFF;
                    int g = (type >> 8) & 0xFF;
                    int b = type & 0xFF;
                    world.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, pos.getX() + 0.75 - world.rand.nextDouble() * 0.5, pos.getY() + 0.5 + 0.75 - world.rand.nextDouble() * 0.5, pos.getZ() + 0.75 - world.rand.nextDouble() * 0.5, r, g, b);
                }
            }
        }
        if (id == 0) {
            if (grid != null) {
                grid.destroy();
                grid = null;
            }
        }
        return true;
    }

    public int getEnergyToDischarge() {
        ItemStack stack = inventory.getStackInSlot(SLOT_DISCHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                return storage.getMaxEnergyStored() - storage.getEnergyStored();
            }
        }
        return 0;
    }

    public int getCapacityToDischarge() {
        ItemStack stack = inventory.getStackInSlot(SLOT_DISCHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                return storage.getMaxEnergyStored();
            }
        }
        return 0;
    }

    public int getEnergyToCharge() {
        ItemStack stack = inventory.getStackInSlot(SLOT_CHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                return storage.getEnergyStored();
            }
        }
        return 0;
    }

    public int getCapacityToCharge() {
        ItemStack stack = inventory.getStackInSlot(SLOT_CHARGE_IN);
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                return storage.getMaxEnergyStored();
            }
        }
        return 0;
    }

    public EnergyStorageBase getEnergyStorageBase() {
        return energy;
    }

    @Override
    public EnergyStorageBase getEnergy() {
        return getBlockMetadata() != 0 ? CreativeEnergyStorage.INSTANCE : grid != null ? grid.getEnergyStorage() : energy;
    }

    private static class CreativeEnergyStorage extends EnergyStorageBase {

        private static final int CAPACITY = Integer.MAX_VALUE;

        private static final CreativeEnergyStorage INSTANCE = new CreativeEnergyStorage();

        public CreativeEnergyStorage() {
            super(CAPACITY);
            this.energy = CAPACITY / 2;
        }

        @Override
        public void setEnergyStored(int energy) {
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return maxReceive;
        }

        @Override
        public int receiveEnergyInternal(int maxReceive, boolean simulate) {
            return maxReceive;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return maxExtract;
        }

        @Override
        public int extractEnergyInternal(int maxExtract, boolean simulate) {
            return maxExtract;
        }

    }
}

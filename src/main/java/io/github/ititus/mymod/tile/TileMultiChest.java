package io.github.ititus.mymod.tile;

import com.google.common.collect.Sets;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.util.grid.IGridMember;
import io.github.ititus.mymod.util.multi.chest.MultiChestGrid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TileMultiChest extends TileBase implements ITickable, IGridMember<MultiChestGrid> {

    private static final int SLOTS = 1, SLOT = 0;
    private static final int[] INPUT_SLOTS = {SLOT}, OUTPUT_SLOTS = {SLOT};

    private final ItemStackHandlerBase inventory = new ItemStackHandlerBase(SLOTS, this);

    private MultiChestGrid grid;

    public TileMultiChest() {
        super(ModBlocks.multiChest.getRegistryName().toString());
    }

    public void checkGrids() {
        Set<MultiChestGrid> grids = Arrays.stream(EnumFacing.values())
                .map(facing -> pos.offset(facing))
                .map(pos -> world.getTileEntity(pos))
                .filter(tile -> tile instanceof TileMultiChest && !tile.isInvalid())
                .map(tile -> (TileMultiChest) tile)
                .map(TileMultiChest::getGrid)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (grids.isEmpty()) {
            grid = new MultiChestGrid();
            grid.addMember(this);
        } else {
            Iterator<MultiChestGrid> iterator = grids.iterator();
            MultiChestGrid firstGrid = iterator.next();

            firstGrid.addMember(this);

            while (iterator.hasNext()) {
                MultiChestGrid grid = iterator.next();
                firstGrid.merge(grid);
            }
        }
    }

    @Override
    public MultiChestGrid getGrid() {
        return grid;
    }

    @Override
    public void setGrid(MultiChestGrid grid) {
        this.grid = grid;
    }

    @Override
    public boolean valid() {
        return !isInvalid();
    }

    @Override
    public long toLong() {
        if (!isInvalid()) {
            return pos.toLong();
        }
        return 0;
    }

    public void showGridParticles() {
        if (grid == null) {
            world.addBlockEvent(pos, world.getBlockState(pos).getBlock(), 2, 0);
        } else {
            Set<TileMultiChest> pipes = grid.getMembers();
            Set<BlockPos> gridPosSet = Sets.newHashSet();

            Iterator<TileMultiChest> iterator = pipes.iterator();
            while (iterator.hasNext()) {
                TileMultiChest multiChest = iterator.next();
                if (multiChest != null && !multiChest.isInvalid()) {
                    gridPosSet.add(multiChest.getPos());
                }
            }

            for (BlockPos pos : gridPosSet) {
                world.addBlockEvent(pos, world.getBlockState(pos).getBlock(), 3, grid.hashCode());
            }

        }
    }

    @Override
    public boolean hasSideConfiguration() {
        return false;
    }

    public int[] getInputSlots() {
        return grid != null ? IntStream.range(0, grid.getSize()).toArray() : INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return grid != null ? IntStream.range(0, grid.getSize()).toArray() : OUTPUT_SLOTS;
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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("inventory")) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound.getTag("inventory"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        return compound;
    }

    public ItemStackHandlerBase getInventoryBase() {
        return inventory;
    }

    public ItemStackHandlerBase getInventory() {
        return grid != null ? grid.getItemStackHandler() : inventory;
    }

}

package io.github.ititus.mymod.tile;

import com.google.common.base.Strings;
import io.github.ititus.mymod.block.BlockContainerBase;
import io.github.ititus.mymod.inventory.ItemHandlerSideWrapper;
import io.github.ititus.mymod.inventory.ItemStackHandlerBase;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import io.github.ititus.mymod.util.network.NetworkState;
import io.github.ititus.mymod.util.side.SideConfiguration;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.EnumMap;

public class TileBase extends TileEntity implements IWorldNameable {

    private static final int[] EMPTY = {};

    protected final String name;
    protected final SideConfiguration sideConfiguration;
    private final EnumMap<EnumFacing, IItemHandler> inventories;
    private final EnumMap<EnumFacing, IEnergyStorage> energies;
    protected String customName;
    private IBlockState state = null;
    private int meta = -1;

    public TileBase(String name) {
        super();
        this.name = "container." + name + ".name";
        this.sideConfiguration = new SideConfiguration();
        this.inventories = new EnumMap<>(EnumFacing.class);
        this.energies = new EnumMap<>(EnumFacing.class);
    }

    public void sync() {
        if (world != null) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 8);
        }
    }

    @Override
    public void markDirty() {
        if (world != null) {
            updateContainingBlockInfo();
            getBlockMetadata();
            world.markChunkDirty(pos, this);
            if (getBlock() != Blocks.AIR) {
                world.updateComparatorOutputLevel(pos, getBlock());
            }
        }
        sync();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        if (oldState.getBlock() != newState.getBlock()) {
            return true;
        }
        if (oldState == newState) {
            return false;
        }
        for (IProperty<?> prop : newState.getPropertyKeys()) {
            if (prop != BlockContainerBase.FACING && !oldState.getValue(prop).equals(newState.getValue(prop))) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return !isInvalid() && player.getDistanceSqToCenter(pos) <= 64;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound compound = pkt.getNbtCompound();
        if (compound != null) {
            readFromNBT(compound);
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, -1, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("customName", Constants.NBT.TAG_STRING)) {
            customName = compound.getString("customName");
        } else {
            customName = null;
        }
        if (hasSideConfiguration() && compound.hasKey("sideConfiguration")) {
            sideConfiguration.deserializeNBT(compound.getCompoundTag("sideConfiguration"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (hasCustomName()) {
            compound.setString("customName", customName);
        }
        if (hasSideConfiguration()) {
            compound.setTag("sideConfiguration", sideConfiguration.serializeNBT());
        }
        return compound;
    }

    @Override
    public String getName() {
        return hasCustomName() ? customName : name;
    }

    public String getDefaultName() {
        return name;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Override
    public boolean hasCustomName() {
        return !Strings.isNullOrEmpty(customName);
    }

    @Override
    public ITextComponent getDisplayName() {
        return hasCustomName() ? new TextComponentString(getCustomName()) : new TextComponentTranslation(getDefaultName());
    }

    public int getComparatorLevel() {
        return 0;
    }

    public boolean hasSideConfiguration() {
        return hasInventory();
    }

    public SideConfiguration getSideConfiguration() {
        return hasSideConfiguration() ? sideConfiguration : null;
    }

    public int[] getInputSlots() {
        return EMPTY;
    }

    public int[] getOutputSlots() {
        return EMPTY;
    }

    public EnumFacing getFacing() {
        return getBlock().hasFacing() ? getBlockState().getValue(BlockContainerBase.FACING) : EnumFacing.NORTH;
    }

    public boolean hasEnergy() {
        return getEnergy() != null;
    }

    public boolean hasEnergy(EnumFacing facing) {
        return getEnergy(facing) != null;
    }

    public EnergyStorageBase getEnergy() {
        return null;
    }

    //TODO: Implement
    public IEnergyStorage getEnergy(EnumFacing facing) {
        if (!hasEnergy()) {
            return null;
        } /*if (facing == null || !hasSideConfiguration()) {*/
        return getEnergy();
        /*}
        if (!energies.containsKey(facing)) {
            IEnergyStorage storage = new EnergyStorageSideWrapper(this::getEnergy, facing);
            energies.put(facing, storage);
            return storage;
        }
        return energies.get(facing);*/
    }

    public boolean hasInventory() {
        return getInventory() != null;
    }

    public boolean hasInventory(EnumFacing facing) {
        return getInventory(facing) != null;
    }

    public ItemStackHandlerBase getInventory() {
        return null;
    }

    public IItemHandler getInventory(EnumFacing facing) {
        if (!hasInventory()) {
            return null;
        }
        if (facing == null || !hasSideConfiguration()) {
            return getInventory();
        }
        if (!inventories.containsKey(facing)) {
            IItemHandler handler = new ItemHandlerSideWrapper(this::getInventory, facing);
            inventories.put(facing, handler);
            return handler;
        }
        return inventories.get(facing);
    }

    public NetworkState getNetworkState() {
        return hasSideConfiguration() ? new NetworkState(0, 0, 1) : null;
    }

    public void updateNetworkState(NetworkState state) {
        if (hasSideConfiguration()) {
            state.set(0, sideConfiguration.copy());
        }
    }

    public void loadFromNetworkState(NetworkState state) {
        if (hasSideConfiguration()) {
            sideConfiguration.set(state.getSideConfiguration(0));
        }
    }

    public NetworkState getGuiState() {
        return hasSideConfiguration() ? new NetworkState(0, 0, 1) : null;
    }

    public void updateGuiState(NetworkState state) {
        state.set(0, sideConfiguration.copy());
    }

    public void loadFromGuiState(NetworkState state) {
        sideConfiguration.set(state.getSideConfiguration(0));
    }

    @Override
    public int getBlockMetadata() {
        if (meta < 0) {
            meta = getBlockType().getMetaFromState(getBlockState());
        }
        return meta;
    }

    public IBlockState getBlockState() {
        if (state == null && world != null) {
            state = world.getBlockState(pos);
        }
        return state;
    }

    @Override
    public Block getBlockType() {
        if (blockType == null) {
            blockType = getBlockState().getBlock();
        }
        return blockType;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        state = null;
        meta = -1;
    }

    public BlockContainerBase getBlock() {
        return (BlockContainerBase) getBlockType();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? hasEnergy(facing) : capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? hasInventory(facing) : super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? (T) getEnergy(facing) : capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) getInventory(facing) : super.getCapability(capability, facing);
    }
}

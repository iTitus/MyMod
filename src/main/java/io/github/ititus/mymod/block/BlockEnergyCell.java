package io.github.ititus.mymod.block;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.init.ModBlocks;
import io.github.ititus.mymod.item.block.ItemBlockEnergyCell;
import io.github.ititus.mymod.tile.TileEnergyCell;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class BlockEnergyCell extends BlockContainerBase {

    public static final PropertyBool CREATIVE = PropertyBool.create("creative");
    public static final PropertyInteger ENERGY_LEVEL = PropertyInteger.create("energy_level", 0, 10);

    public BlockEnergyCell() {
        super(MyMod.ENERGY_CELL);
        setDefaultState(getDefaultState().withProperty(CREATIVE, false).withProperty(ENERGY_LEVEL, 0));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEnergyCell();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() == Items.STICK) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileEnergyCell) {
                    ((TileEnergyCell) tile).showGridParticles();
                }
            } else {
                player.openGui(MyMod.instance, GuiHandler.ENERGY_CELL, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!state.getValue(CREATIVE) && block == ModBlocks.energyCell) {
            int cells = 1;
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos pos_ = fromPos.offset(facing);
                if (!pos_.equals(pos)) {
                    IBlockState state_ = world.getBlockState(pos_);
                    if (state_.getBlock() == ModBlocks.energyCell && !state_.getValue(CREATIVE)) {
                        cells++;
                        break;
                    }
                }
            }
            if (cells > 1) {
                world.addBlockEvent(pos, state.getBlock(), 0, 0);
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(CREATIVE, meta != 0);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!world.isRemote && !state.getValue(CREATIVE)) {
            if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage itemStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
                if (itemStorage instanceof EnergyStorageBase) {
                    EnergyStorageBase itemEnergy = (EnergyStorageBase) itemStorage;
                    TileEntity tile = world.getTileEntity(pos);
                    if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage tileStorage = tile.getCapability(CapabilityEnergy.ENERGY, null);
                        if (tileStorage instanceof EnergyStorageBase) {
                            EnergyStorageBase tileEnergy = (EnergyStorageBase) tileStorage;
                            tileEnergy.setEnergyStored(itemEnergy.getEnergyStored());
                            if (tileEnergy.getEnergyStored() > 0) {
                                world.notifyBlockUpdate(pos, state, state, 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean dropInventory() {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        if (!drops.isEmpty()) {
            ItemStack stack = drops.get(0);
            if (!stack.isEmpty()) {
                chargeStack(stack, world, pos, state);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getChargedStack(world, pos, state);
    }

    private ItemStack getChargedStack(IBlockAccess world, BlockPos pos, IBlockState state) {
        ItemStack stack = new ItemStack(this);
        chargeStack(stack, world, pos, state);
        return stack;
    }

    private void chargeStack(ItemStack stack, IBlockAccess world, BlockPos pos, IBlockState state) {
        boolean creative = state.getValue(CREATIVE);
        stack.setItemDamage(creative ? 1 : 0);
        if (!creative && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage itemStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (itemStorage instanceof EnergyStorageBase) {
                EnergyStorageBase itemEnergy = (EnergyStorageBase) itemStorage;
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
                    IEnergyStorage tileStorage = tile.getCapability(CapabilityEnergy.ENERGY, null);
                    if (tileStorage instanceof EnergyStorageBase) {
                        EnergyStorageBase tileEnergy = (EnergyStorageBase) tileStorage;
                        itemEnergy.setEnergyStored(tileEnergy.getEnergyStored());
                    }
                }
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return !state.getValue(CREATIVE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CREATIVE, ENERGY_LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(CREATIVE, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(CREATIVE) ? 1 : 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        int energyLevel = 0;
        if (!state.getValue(CREATIVE)) {
            TileEntity tile = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
            if (tile instanceof TileEnergyCell) {
                energyLevel = ((TileEnergyCell) tile).energyLevel;
            }
        }
        return energyLevel > 0 ? state.withProperty(ENERGY_LEVEL, energyLevel) : state;
    }

    @Override
    public Item getItemBlock() {
        return new ItemBlockEnergyCell(this);
    }
}

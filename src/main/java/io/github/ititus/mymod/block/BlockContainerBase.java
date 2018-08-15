package io.github.ititus.mymod.block;

import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public abstract class BlockContainerBase extends BlockBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockContainerBase(String name) {
        super(name);
        this.hasTileEntity = true;
        setDefaultState(hasFacing() ? blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH) : blockState.getBaseState());
    }

    public BlockContainerBase(String name, Material material) {
        super(name, material);
        this.hasTileEntity = true;
        setDefaultState(hasFacing() ? blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH) : blockState.getBaseState());
    }

    public BlockContainerBase(String name, Material material, SoundType soundType) {
        super(name, material, soundType);
        this.hasTileEntity = true;
        setDefaultState(hasFacing() ? blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH) : blockState.getBaseState());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileBase) {
                ((TileBase) tile).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest ? true : super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        TileEntity tile = null;
        if (dropWithCustomName() && !drops.isEmpty()) {
            tile = world.getTileEntity(pos);
            if (tile instanceof IWorldNameable) {
                IWorldNameable nameable = (IWorldNameable) tile;
                if (nameable.hasCustomName()) {
                    ItemStack stack = drops.get(0);
                    if (!stack.isEmpty()) {
                        stack.setStackDisplayName(nameable.getName());
                    }
                }
            }
        }
        if (dropInventory()) {
            if (tile == null) {
                tile = world.getTileEntity(pos);
            }
            if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null) {
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            drops.add(stack);
                        }
                    }
                }
            }
        }
    }

    protected boolean dropInventory() {
        return false;
    }

    protected boolean dropWithCustomName() {
        return true;
    }

    public boolean hasFacing() {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, hasFacing() ? new IProperty<?>[]{FACING} : new IProperty<?>[0]);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return hasFacing() ? getDefaultState().withProperty(FACING, EnumFacing.getFront(meta)) : getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return hasFacing() ? state.getValue(FACING).getIndex() : 0;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        if (hasComparatorInputOverride(state)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileBase) {
                return ((TileBase) tile).getComparatorLevel();
            }
        }
        return super.getComparatorInputOverride(state, world, pos);
    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tile = world.getTileEntity(pos);
        return tile != null && tile.receiveClientEvent(id, param);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return hasFacing() ? getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()) : getDefaultState();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rotation) {
        return hasFacing() ? state.withProperty(FACING, rotation.rotate(state.getValue(FACING))) : state;
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return hasFacing() ? state.withRotation(mirror.toRotation(state.getValue(FACING))) : state;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

}

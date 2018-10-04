package io.github.ititus.mymod.handler;

import io.github.ititus.mymod.client.gui.*;
import io.github.ititus.mymod.inventory.container.*;
import io.github.ititus.mymod.item.ItemBackpack;
import io.github.ititus.mymod.tile.*;
import io.github.ititus.mymod.util.backpack.Backpack;
import io.github.ititus.mymod.util.backpack.BackpackManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class GuiHandler implements IGuiHandler {

    public static final int FALL_GEN = 0;
    public static final int ENERGY_CELL = 1;
    public static final int PULVERIZER = 2;
    public static final int TANK = 3;
    public static final int MULTI_CHEST = 4;
    public static final int SIDE_CONFIGURATOR = 5;
    public static final int BACKPACK = 6;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile;
        switch (ID) {
            case FALL_GEN:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileFallGen) {
                    return new ContainerFallGen(player, (TileFallGen) tile);
                }
                break;
            case ENERGY_CELL:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEnergyCell) {
                    return new ContainerEnergyCell(player, (TileEnergyCell) tile);
                }
                break;
            case PULVERIZER:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TilePulverizer) {
                    return new ContainerPulverizer(player, (TilePulverizer) tile);
                }
                break;
            case TANK:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileTank) {
                    return new ContainerTank(player, (TileTank) tile);
                }
                break;
            case MULTI_CHEST:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileMultiChest) {
                    return new ContainerMultiChest(player, (TileMultiChest) tile);
                }
                break;
            case SIDE_CONFIGURATOR:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileBase && ((TileBase) tile).hasSideConfiguration()) {
                    return new ContainerSideConfigurator(player, (TileBase) tile);
                }
                break;
            case BACKPACK:
                ItemStack stack = player.getHeldItem(x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                if (!stack.isEmpty() && stack.getItem() instanceof ItemBackpack) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    if (nbt != null && nbt.hasKey("backpack", Constants.NBT.TAG_COMPOUND)) {
                        NBTTagCompound compound = nbt.getCompoundTag("backpack");
                        UUID uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
                        if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                            Backpack backpack = BackpackManager.get(world).getBackpack(uuid);
                            if (backpack != null /*&& backpack can be opened*/) {
                                return new ContainerBackpack(player, stack, backpack);
                            }
                        }
                    }
                }
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile;
        switch (ID) {
            case FALL_GEN:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileFallGen) {
                    return new GuiFallGen(new ContainerFallGen(player, (TileFallGen) tile));
                }
                break;
            case ENERGY_CELL:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEnergyCell) {
                    return new GuiEnergyCell(new ContainerEnergyCell(player, (TileEnergyCell) tile));
                }
                break;
            case PULVERIZER:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TilePulverizer) {
                    return new GuiPulverizer(new ContainerPulverizer(player, (TilePulverizer) tile));
                }
                break;
            case TANK:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileTank) {
                    return new GuiTank(new ContainerTank(player, (TileTank) tile));
                }
                break;
            case MULTI_CHEST:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileMultiChest) {
                    return new GuiMultiChest(new ContainerMultiChest(player, (TileMultiChest) tile));
                }
                break;
            case SIDE_CONFIGURATOR:
                tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileBase && ((TileBase) tile).hasSideConfiguration()) {
                    return new GuiSideConfigurator(new ContainerSideConfigurator(player, (TileBase) tile));
                }
                break;
            case BACKPACK:
                ItemStack stack = player.getHeldItem(x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                if (!stack.isEmpty() && stack.getItem() instanceof ItemBackpack) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    if (nbt != null && nbt.hasKey("backpack", Constants.NBT.TAG_COMPOUND)) {
                        NBTTagCompound compound = nbt.getCompoundTag("backpack");
                        UUID uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
                        if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                            //TODO: find out if saving on client is even required...
                            Backpack backpack = new Backpack(BackpackManager.get(world)); // BackpackManager.get(world).getOrCreateBackpack(uuid);
                            return new GuiBackpack(new ContainerBackpack(player, stack, backpack));
                        }
                    }
                }
                break;
        }
        return null;
    }
}

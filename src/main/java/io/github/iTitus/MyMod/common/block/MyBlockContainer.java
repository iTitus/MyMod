package io.github.iTitus.MyMod.common.block;

import io.github.iTitus.MyMod.common.tileentity.MyTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class MyBlockContainer extends MyBlock implements
        ITileEntityProvider {

    protected MyBlockContainer(EnumBlockType type) {
        super(type);
        isBlockContainer = true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block,
                           int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z,
                                        int eventNumber, int argument) {

        super.onBlockEventReceived(world, x, y, z, eventNumber, argument);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(eventNumber,
                argument) : false;

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z,
                                EntityLivingBase entityLiving, ItemStack stack) {

        MyTileEntity tile = ((MyTileEntity) world.getTileEntity(x, y, z));

        int facing;
        if (is6Sided() && entityLiving.rotationPitch > 45)
            facing = 4;
        else if (is6Sided() && entityLiving.rotationPitch < -45)
            facing = 5;
        else
            facing = MathHelper
                    .floor_double(entityLiving.rotationYaw / 90F + 0.5D) & 3;

        int direction = 0;

        switch (facing) {
            case 0:
                direction = ForgeDirection.NORTH.ordinal();
                break;
            case 1:
                direction = ForgeDirection.EAST.ordinal();
                break;
            case 2:
                direction = ForgeDirection.SOUTH.ordinal();
                break;
            case 3:
                direction = ForgeDirection.WEST.ordinal();
                break;
            case 4:
                direction = ForgeDirection.UP.ordinal();
                break;
            case 5:
                direction = ForgeDirection.DOWN.ordinal();
                break;
            default:
                break;
        }

        tile.setOrientation(direction);

        if (stack.hasDisplayName())
            tile.setCustomName(stack.getDisplayName());

        tile.setOwner((entityLiving instanceof EntityPlayer) ? ((EntityPlayer) entityLiving)
                .getDisplayName() : entityLiving.getCommandSenderName());

    }

}

package io.github.ititus.mymod.util.weight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

public class MassHelper {

    public static double getEntityMass(Entity entity) {
        return getEntityVolume(entity) * getEntityDensity(entity);
    }

    public static double getEntityDensity(Entity entity) {
        if (entity instanceof EntityFallingBlock) {
            EntityFallingBlock falling = (EntityFallingBlock) entity;
            return getBlockDensity(falling.getBlock(), falling.tileEntityData);
        }
        return 1;
    }

    public static double getEntityVolume(Entity entity) {
        AxisAlignedBB aabb = entity.getEntityBoundingBox();
        double sizeX = Math.abs(aabb.maxX - aabb.minX);
        double sizeY = Math.abs(aabb.maxY - aabb.minY);
        double sizeZ = Math.abs(aabb.maxZ - aabb.minZ);
        double volume = sizeX * sizeY * sizeZ;
        if (volume > 0) {
            return volume;
        }
        return entity.width * entity.width * entity.height;
    }

    public static double getBlockDensity(IBlockState state, NBTTagCompound tileData) {
        Material m = state.getMaterial();
        if (m == Material.ANVIL || m == Material.IRON) {
            return 3;
        }
        if (m == Material.ROCK || m == Material.GROUND || m == Material.SAND) {
            return 2;
        }
        return 1;
    }

}

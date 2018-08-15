package io.github.ititus.mymod.util.weight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;

public class WeightHelper {

    public static int getWeight(Entity entity) {
        if (entity instanceof EntityFallingBlock) {
            EntityFallingBlock falling = (EntityFallingBlock) entity;
            return getWeight(falling.getBlock(), falling.tileEntityData);
        }
        return 1;
    }

    public static int getWeight(IBlockState state, NBTTagCompound tileData) {
        return 1;
    }

}

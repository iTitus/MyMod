package io.github.iTitus.MyMod.common.entity.gun;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;

public class EntityBulletCasing extends EntityArrow {

    public EntityBulletCasing(EntityPlayer player) {
        super(player.worldObj);

        setPosition(player.posX, player.posY + 0.8, player.posZ);

        motionX = rand.nextGaussian() * 0.05;
        motionY = rand.nextGaussian() * 0.05 + 0.2F;
        motionZ = rand.nextGaussian() * 0.05;

        setDamage(0);
    }

    @Override
    public boolean getIsCritical() {
        return false;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
    }

}

package com.iTitus.MyMod.client.particle;

import java.util.Random;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityBulletCasingFX extends EntityFX {

	public EntityBulletCasingFX(World world, double x, double y, double z,
			double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}

	public static EntityBulletCasingFX makeCasing(EntityPlayer player) {

		Random rand = new Random();

		EntityBulletCasingFX bulletCasing = new EntityBulletCasingFX(
				player.worldObj,
				player.posX + (rand.nextFloat() * 0.8F + 0.1F), player.posY
						+ (rand.nextFloat() * 0.8F + 0.1F), player.posZ
						+ (rand.nextFloat() * 0.8F + 0.1F),
				rand.nextGaussian() * 0.05F,
				rand.nextGaussian() * 0.05F + 0.2F, rand.nextGaussian() * 0.05F);

		return bulletCasing;
	}

}

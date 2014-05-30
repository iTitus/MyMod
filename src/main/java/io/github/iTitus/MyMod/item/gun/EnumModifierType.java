package io.github.iTitus.MyMod.item.gun;

import io.github.iTitus.MyMod.entity.gun.EntityBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public enum EnumModifierType {

	gunpowder {

		@Override
		public void onShoot(EntityBullet bullet, int count) {
			double d = count * 1.1;
			bullet.motionX *= d;
			bullet.motionY *= d;
			bullet.motionZ *= d;
		}

		@Override
		public ItemStack getItemStack() {
			return new ItemStack(Items.gunpowder);
		}

		@Override
		public int getMaxCount() {
			return 3;
		}

		@Override
		public void onUpdate(EntityBullet bullet, int count) {
		}

		@Override
		public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
				int count) {
		}

		@Override
		public boolean strictMeta() {
			return false;
		}

		@Override
		public boolean strictNBT() {
			return false;
		}

	},
	tnt {

		@Override
		public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
				int count) {

			if (!bullet.worldObj.isRemote) {
				bullet.worldObj.createExplosion(bullet, mop.hitVec.xCoord,
						mop.hitVec.yCoord, mop.hitVec.zCoord, 2F, false);
				bullet.setDead();
			}

		}

		@Override
		public ItemStack getItemStack() {
			return new ItemStack(Blocks.tnt);
		}

		@Override
		public int getMaxCount() {
			return 1;
		}

		@Override
		public void onUpdate(EntityBullet bullet, int count) {
		}

		@Override
		public void onShoot(EntityBullet bullet, int count) {
		}

		@Override
		public boolean strictMeta() {
			return false;
		}

		@Override
		public boolean strictNBT() {
			return false;
		}

	},
	diamond_sword {

		@Override
		public ItemStack getItemStack() {
			return new ItemStack(Items.diamond_sword);
		}

		@Override
		public int getMaxCount() {
			return 1;
		}

		@Override
		public void onUpdate(EntityBullet bullet, int count) {
		}

		@Override
		public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
				int count) {

			if (mop.entityHit != null
					&& (mop.entityHit instanceof EntityLivingBase)
					|| mop.entityHit instanceof EntityMinecart
					|| mop.entityHit instanceof EntityBoat) {

				Entity e = mop.entityHit;

				e.attackEntityFrom(
						DamageSource.causePlayerDamage(bullet.getShooter()),
						((ItemSword) Items.diamond_sword).func_150931_i());

			}

		}

		@Override
		public void onShoot(EntityBullet bullet, int count) {
		}

		@Override
		public boolean strictMeta() {
			return false;
		}

		@Override
		public boolean strictNBT() {
			return false;
		}
	},
	fire {

		@Override
		public ItemStack getItemStack() {
			return new ItemStack(Items.flint_and_steel);
		}

		@Override
		public boolean strictMeta() {
			return false;
		}

		@Override
		public boolean strictNBT() {
			return false;
		}

		@Override
		public int getMaxCount() {
			return 1;
		}

		@Override
		public void onUpdate(EntityBullet bullet, int count) {

		}

		@Override
		public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
				int count) {

		}

		@Override
		public void onShoot(EntityBullet bullet, int count) {
			bullet.setIsFirey(true);
		}
	};

	public abstract ItemStack getItemStack();

	public abstract boolean strictMeta();

	public abstract boolean strictNBT();

	public abstract int getMaxCount();

	public abstract void onUpdate(EntityBullet bullet, int count);

	public abstract void onImpact(EntityBullet bullet,
			MovingObjectPosition mop, int count);

	public abstract void onShoot(EntityBullet bullet, int count);

	public static boolean isModifier(ItemStack stack) {
		return getForStack(stack) != null;
	}

	public static EnumModifierType getForStack(ItemStack stack) {

		if (stack != null) {

			for (EnumModifierType modifier : values()) {

				if (stack.getItem() != modifier.getItemStack().getItem())
					continue;

				if (modifier.strictMeta()
						&& modifier.getItemStack().getItemDamage() != stack
								.getItemDamage())
					continue;

				if (modifier.strictNBT()
						&& !ItemStack.areItemStackTagsEqual(
								modifier.getItemStack(), stack))
					continue;

				return modifier;

			}
		}

		return null;
	}

}

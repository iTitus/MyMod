package com.iTitus.MyMod.item.gun;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

import com.iTitus.MyMod.entity.EntityBullet;

public enum EnumModifierType {

	gunpowder {

		@Override
		public void onShoot(EntityBullet bullet, int count) {
			double d = Math.pow(1.5, count);
			bullet.addVelocity(bullet.motionX * d, bullet.motionY * d,
					bullet.motionZ * d);
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

	};

	public abstract ItemStack getItemStack();

	public abstract int getMaxCount();

	public abstract void onUpdate(EntityBullet bullet, int count);

	public abstract void onImpact(EntityBullet bullet,
			MovingObjectPosition mop, int count);

	public abstract void onShoot(EntityBullet bullet, int count);

	public static boolean isModifier(ItemStack stack) {

		for (EnumModifierType modifier : values()) {
			if (ItemStack.areItemStacksEqual(stack.copy().splitStack(1),
					modifier.getItemStack().copy().splitStack(1))) {
				return true;
			}
		}

		return false;
	}

	public static EnumModifierType getForStack(ItemStack stack) {

		if (isModifier(stack)) {
			for (EnumModifierType modifier : values()) {
				if (ItemStack.areItemStacksEqual(stack.copy().splitStack(1),
						modifier.getItemStack().copy().splitStack(1))) {
					return modifier;
				}
			}
		}
		return null;
	}

}

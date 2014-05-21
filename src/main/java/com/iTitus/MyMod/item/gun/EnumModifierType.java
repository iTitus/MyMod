package com.iTitus.MyMod.item.gun;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

import com.iTitus.MyMod.entity.EntityBullet;

public enum EnumModifierType {

	gunpowder(new ItemStack(Items.gunpowder), 3) {

		@Override
		public void onShoot(EntityBullet bullet, int count) {
			double d = Math.pow(1.5, count);
			bullet.addVelocity(bullet.motionX * d, bullet.motionY * d,
					bullet.motionZ * d);
		}

	},
	tnt(new ItemStack(Blocks.tnt), 1) {

		@Override
		public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
				int count) {

			if (!bullet.worldObj.isRemote) {
				bullet.worldObj.createExplosion(bullet, mop.hitVec.xCoord,
						mop.hitVec.yCoord, mop.hitVec.zCoord, 2F, false);
				bullet.setDead();
			}

		}

	};

	private ItemStack stack;
	private int maxStackSize;

	private EnumModifierType(ItemStack stack, int maxStackSize) {
		this.stack = stack;
		this.maxStackSize = maxStackSize;
	}

	public ItemStack getItemStack() {
		return stack;
	}

	public int getMaxCount() {
		return maxStackSize;
	}

	public void onUpdate(EntityBullet bullet, int count) {
	}

	public void onImpact(EntityBullet bullet, MovingObjectPosition mop,
			int count) {
	}

	public void onShoot(EntityBullet bullet, int count) {
	}

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

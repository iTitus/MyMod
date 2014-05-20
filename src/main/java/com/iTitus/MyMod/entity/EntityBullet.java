package com.iTitus.MyMod.entity;

import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.iTitus.MyMod.item.gun.EnumModifierType;
import com.iTitus.MyMod.item.gun.ItemAmmo;

public class EntityBullet extends EntitySnowball {

	private HashMap<EnumModifierType, Integer> modifiers;

	public EntityBullet(World world, EntityLivingBase entity,
			HashMap<EnumModifierType, Integer> modifiers) {
		super(world, entity);
		this.modifiers = modifiers;
		onShoot();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		for (EnumModifierType modifier : modifiers.keySet()) {
			modifier.onImpact(this, mop, modifiers.get(modifier));
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		for (EnumModifierType modifier : modifiers.keySet()) {
			modifier.onUpdate(this, modifiers.get(modifier));
		}
	}

	private void onShoot() {
		for (EnumModifierType modifier : modifiers.keySet()) {
			modifier.onShoot(this, modifiers.get(modifier));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		ItemAmmo.writeToNBT(modifiers, nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		modifiers = ItemAmmo.readFromNBT(nbt);
	}

}

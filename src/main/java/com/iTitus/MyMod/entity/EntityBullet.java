package com.iTitus.MyMod.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.iTitus.MyMod.item.gun.EnumModifierType;
import com.iTitus.MyMod.item.gun.ItemAmmo;
import com.iTitus.MyMod.item.gun.Modifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityBullet extends EntitySnowball { // EntityThrowable {

	private ArrayList<Modifier> modifiers;

	public EntityBullet(World world, EntityLivingBase entity,
			ArrayList<Modifier> modifiers) {
		super(world, entity);
		this.modifiers = modifiers;
		onShoot();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		for (Modifier modifier : modifiers) {
			modifier.getModifierType().onImpact(this, mop, modifier.getCount());
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		for (Modifier modifier : modifiers) {
			modifier.getModifierType().onUpdate(this, modifier.getCount());
		}
	}

	private void onShoot() {
		for (Modifier modifier : modifiers) {
			modifier.getModifierType().onShoot(this, modifier.getCount());
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

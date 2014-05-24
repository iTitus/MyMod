package com.iTitus.MyMod.entity.gun;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.iTitus.MyMod.item.gun.EnumModifierType;
import com.iTitus.MyMod.item.gun.ItemAmmo;

public class EntityBullet extends EntitySnowball {

	private HashMap<EnumModifierType, Integer> modifiers;
	private EntityPlayer shooter;

	public EntityBullet(World world, EntityPlayer shooter,
			HashMap<EnumModifierType, Integer> modifiers) {
		super(world, shooter);
		this.modifiers = modifiers;
		this.shooter = shooter;
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

	public EntityBullet onShoot() {
		for (EnumModifierType modifier : modifiers.keySet()) {
			modifier.onShoot(this, modifiers.get(modifier));
		}
		return this;
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

	public EntityPlayer getShooter() {
		return shooter;
	}

}

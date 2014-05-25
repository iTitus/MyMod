package io.github.iTitus.MyMod.entity.gun;

import io.github.iTitus.MyMod.item.gun.EnumModifierType;
import io.github.iTitus.MyMod.item.gun.ItemAmmo;

import java.util.HashMap;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {

	private static final String TAG_SHOOTER = "shooter";
	private static final int ID_FIREY = 2;

	private HashMap<EnumModifierType, Integer> modifiers;
	private EntityPlayer shooter;

	public EntityBullet(World world) {
		super(world);
	}

	public EntityBullet(World world, EntityPlayer shooter,
			HashMap<EnumModifierType, Integer> modifiers) {
		super(world, shooter);
		this.modifiers = modifiers;
		this.shooter = shooter;
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		dataWatcher.addObject(ID_FIREY, Byte.valueOf((byte) 0));

	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if (modifiers != null) {
			for (EnumModifierType modifier : modifiers.keySet()) {
				modifier.onImpact(this, mop, modifiers.get(modifier));
			}
		}

		for (int i = 0; i < 8; ++i) {
			this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY,
					this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (isBurning() && !worldObj.isRemote) {
			if (mop.typeOfHit == MovingObjectType.BLOCK) {
				BlockCauldron c;

				int x = mop.blockX, y = mop.blockY, z = mop.blockZ;

				switch (mop.sideHit) {
				case 0: {
					y--;
					break;
				}
				case 1: {
					y++;
					break;
				}
				case 2: {
					z--;
					break;
				}
				case 3: {
					z++;
					break;
				}
				case 4: {
					x--;
					break;
				}
				case 5: {
					x++;
					break;
				}
				default:
					break;
				}

				if (worldObj.isAirBlock(x, y, z)) {
					worldObj.playSoundEffect(x + 0.5D,
							y + 0.5D, z + 0.5D,
							"fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					worldObj.setBlock(x, y, z, Blocks.fire);
				}

			} else if (mop.typeOfHit == MovingObjectType.ENTITY
					&& mop.entityHit != null) {
				if (!mop.entityHit.isImmuneToFire()
						&& mop.entityHit.attackEntityFrom(DamageSource.onFire,
								5.0F)) {
					mop.entityHit.setFire(5);
				}

			}
		}

		setDead();

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (modifiers != null) {
			for (EnumModifierType modifier : modifiers.keySet()) {
				modifier.onUpdate(this, modifiers.get(modifier));
			}
		}
		// System.out.println((shooter != null) ? (shooter.getDisplayName())
		// : "null");
	}

	public EntityBullet onShoot() {
		if (modifiers != null) {
			for (EnumModifierType modifier : modifiers.keySet()) {
				modifier.onShoot(this, modifiers.get(modifier));
			}
		}

		return this;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		ItemAmmo.writeToNBT(modifiers, nbt);
		nbt.setTag(TAG_SHOOTER, shooter.getEntityData());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		modifiers = ItemAmmo.readFromNBT(nbt);
		shooter.readFromNBT(nbt.getCompoundTag(TAG_SHOOTER));
	}

	public EntityPlayer getShooter() {
		return shooter;
	}

	@Override
	public boolean isBurning() {
		return super.isBurning() || getIsFirey();
	}

	public void setIsFirey(boolean firey) {
		byte b0 = dataWatcher.getWatchableObjectByte(ID_FIREY);

		if (firey) {
			dataWatcher.updateObject(ID_FIREY, Byte.valueOf((byte) (b0 | 1)));
		} else {
			dataWatcher.updateObject(ID_FIREY, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	public boolean getIsFirey() {
		byte b0 = dataWatcher.getWatchableObjectByte(ID_FIREY);
		return (b0 & 1) != 0;
	}

}

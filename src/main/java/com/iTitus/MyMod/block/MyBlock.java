package com.iTitus.MyMod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.iTitus.MyMod.helper.InventoryHelper;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.lib.MyCreativeTab;
import com.iTitus.MyMod.tileentity.MyTileEntity;

public abstract class MyBlock extends Block {

	protected MyBlock(EnumBlockType type) {
		super(type.material);
		setBlockTextureName(LibTextures.getTextureLoc(type));
		setBlockName(type.name);
		setHardness(type.hardness);
		setResistance(type.resistance);
		setStepSound(type.soundType);
		setLightLevel(type.lightLevel);
		setLightOpacity(type.lightOpacity);
		if (type.putInTab)
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {
		if (dropAllItems())
			InventoryHelper.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public int damageDropped(int dmg) {
		return super.damageDropped(0);
	}

	public boolean dropAllItems() {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityLiving, ItemStack stack) {
		if (world.getTileEntity(x, y, z) instanceof MyTileEntity) {
			int direction = 0;
			int facing = MathHelper
					.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

			if (facing == 0) {
				direction = ForgeDirection.NORTH.ordinal();
			} else if (facing == 1) {
				direction = ForgeDirection.EAST.ordinal();
			} else if (facing == 2) {
				direction = ForgeDirection.SOUTH.ordinal();
			} else if (facing == 3) {
				direction = ForgeDirection.WEST.ordinal();
			}

			if (stack.hasDisplayName()) {
				((MyTileEntity) world.getTileEntity(x, y, z))
						.setCustomName(stack.getDisplayName());
			}

			((MyTileEntity) world.getTileEntity(x, y, z))
					.setOrientation(direction);
		}
	}

}

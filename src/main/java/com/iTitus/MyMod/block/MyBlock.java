package com.iTitus.MyMod.block;

import com.iTitus.MyMod.helper.ItemHelper;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.lib.MyCreativeTab;
import com.iTitus.MyMod.tileentiy.MyTileEntity;

import scala.collection.SetLike;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
		if (putInTab())
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	public boolean putInTab() {
		return true;
	}

	@Override
	public int damageDropped(int dmg) {
		return super.damageDropped(0);
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

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {
		if (dropAllItems())
			ItemHelper.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	public boolean dropAllItems() {
		return true;
	}

}

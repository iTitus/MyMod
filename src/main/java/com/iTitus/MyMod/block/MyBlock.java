package com.iTitus.MyMod.block;

import com.iTitus.MyMod.lib.MyCreativeTab;

import scala.collection.SetLike;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class MyBlock extends Block {

	protected MyBlock(EnumBlockType type) {
		super(type.material);
		setBlockName(type.name);
		setHardness(type.hardness);
		setResistance(type.resistance);
		setStepSound(type.soundType);
		setLightLevel(type.lightLevel);
		setLightOpacity(type.lightOpacity);
		if (putInTab())
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	public abstract boolean putInTab();

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return super.getIcon(side, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@Override
	public int damageDropped(int dmg) {
		return super.damageDropped(0);
	}

}

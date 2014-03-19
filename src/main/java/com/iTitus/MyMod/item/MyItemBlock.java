package com.iTitus.MyMod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.lib.LibTextures;

public abstract class MyItemBlock extends ItemBlock {

	public MyItemBlock(Block block, EnumBlockType type) {
		super(block);
		setUnlocalizedName(type.name);
		setTextureName(LibTextures.getTextureLoc(type));
	}

	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}

}

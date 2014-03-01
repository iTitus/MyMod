package com.iTitus.MyMod.item;

import com.iTitus.MyMod.block.EnumBlockType;
import com.iTitus.MyMod.block.ModBlocks;
import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.lib.MyCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

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

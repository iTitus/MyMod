package io.github.iTitus.MyMod.common.item;

import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.lib.LibTextures;
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

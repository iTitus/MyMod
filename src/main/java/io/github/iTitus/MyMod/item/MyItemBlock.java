package io.github.iTitus.MyMod.item;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.lib.LibTextures;
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

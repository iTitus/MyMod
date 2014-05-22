package com.iTitus.MyMod.item;

import net.minecraft.item.Item;

import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.lib.MyCreativeTab;

public abstract class MyItem extends Item {

	public MyItem(EnumItemType type) {
		super();
		setUnlocalizedName(type.name);
		if (type.hasTexture)
			setTextureName(LibTextures.getTextureLoc(type));
		if (type.putInTab)
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

}

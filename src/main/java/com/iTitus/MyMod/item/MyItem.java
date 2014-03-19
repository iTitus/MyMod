package com.iTitus.MyMod.item;

import net.minecraft.item.Item;

import com.iTitus.MyMod.lib.LibTextures;
import com.iTitus.MyMod.lib.MyCreativeTab;

public abstract class MyItem extends Item {

	public MyItem(EnumItemType type) {
		super();
		setUnlocalizedName(type.name);
		setTextureName(LibTextures.getTextureLoc(type));
		if (putInTab())
			setCreativeTab(MyCreativeTab.INSTANCE);
	}

	public boolean putInTab() {
		return true;
	}

}

package io.github.iTitus.MyMod.common.item;

import io.github.iTitus.MyMod.common.lib.LibTextures;
import io.github.iTitus.MyMod.common.lib.MyCreativeTab;
import net.minecraft.item.Item;

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

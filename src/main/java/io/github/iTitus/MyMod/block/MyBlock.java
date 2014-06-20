package io.github.iTitus.MyMod.block;

import io.github.iTitus.MyMod.lib.LibTextures;
import io.github.iTitus.MyMod.lib.MyCreativeTab;
import io.github.iTitus.MyMod.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.world.World;

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
			InventoryUtil.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

	public boolean dropAllItems() {
		return true;
	}

	public boolean is6Sided() {
		return false;
	}

}

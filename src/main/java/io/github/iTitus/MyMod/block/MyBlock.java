package io.github.iTitus.MyMod.block;

import io.github.iTitus.MyMod.lib.LibTextures;
import io.github.iTitus.MyMod.lib.MyCreativeTab;
import io.github.iTitus.MyMod.tileentity.MyTileEntity;
import io.github.iTitus.MyMod.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
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
		return super.damageDropped(0);
	}

	public boolean dropAllItems() {
		return true;
	}

	public boolean is6Sided() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityLiving, ItemStack stack) {

		MyTileEntity te = ((MyTileEntity) world.getTileEntity(x, y, z));

		int facing;
		if (is6Sided() && entityLiving.rotationPitch > 45)
			facing = 4;
		else if (is6Sided() && entityLiving.rotationPitch < -45)
			facing = 5;
		else
			facing = MathHelper
					.floor_double(entityLiving.rotationYaw / 90F + 0.5D) & 3;

		int direction = 0;

		switch (facing) {
		case 0:
			direction = ForgeDirection.NORTH.ordinal();
			break;
		case 1:
			direction = ForgeDirection.EAST.ordinal();
			break;
		case 2:
			direction = ForgeDirection.SOUTH.ordinal();
			break;
		case 3:
			direction = ForgeDirection.WEST.ordinal();
			break;
		case 4:
			direction = ForgeDirection.UP.ordinal();
			break;
		case 5:
			direction = ForgeDirection.DOWN.ordinal();
			break;
		default:
			break;
		}

		te.setOrientation(direction);

		if (stack.hasDisplayName())
			te.setCustomName(stack.getDisplayName());

	}

}

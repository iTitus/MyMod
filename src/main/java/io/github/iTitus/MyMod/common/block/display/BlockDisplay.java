package io.github.iTitus.MyMod.common.block.display;

import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.block.MyBlockContainer;
import io.github.iTitus.MyMod.common.tileentity.display.TileEntityDisplay;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDisplay extends MyBlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon frontIconOn, frontIconOff;

	public BlockDisplay() {
		super(EnumBlockType.DISPLAY);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDisplay();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x,
			int y, int z) {

		setBlockBoundsBasedOnState(world, x, y, z);

		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x
				+ maxX, y + maxY, z + maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		TileEntityDisplay display = (TileEntityDisplay) world.getTileEntity(x,
				y, z);
		if (display.isAttached() && side == display.getOrientation().ordinal()) {
			if (display.isActive())
				return frontIconOn;
			else
				return frontIconOff;
		} else
			return super.getIcon(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return ((TileEntityDisplay) world.getTileEntity(x, y, z)).isActive() ? 15
				: 0;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x,
			int y, int z) {

		setBlockBoundsBasedOnState(world, x, y, z);

		if (isStanding(world, x, y, z))
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x
					+ maxX, y + maxY + (7 / 16D), z + maxZ);
		else if (isHanging(world, x, y, z))
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY - (7 / 16D),
					z + minZ, x + maxX, y + maxY, z + maxZ);

		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x
				+ maxX, y + maxY, z + maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 3; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public boolean isAttached(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 0;
	}

	public boolean isHanging(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 2;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isStanding(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 1;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityLiving, ItemStack stack) {

		super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);

		TileEntityDisplay tile = ((TileEntityDisplay) world.getTileEntity(x, y,
				z));
		tile.setAttachingMode(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		frontIconOn = register.registerIcon(getTextureName() + "_frontOn");
		frontIconOff = register.registerIcon(getTextureName() + "_frontOff");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {

		TileEntityDisplay tile = (TileEntityDisplay) world.getTileEntity(x, y,
				z);

		if (tile != null) {

			float minX = 0;
			float minY = 0;
			float minZ = 0;
			float maxX = 1;
			float maxY = 1;
			float maxZ = 1;

			if (tile.isAttached()) {
				switch (tile.getOrientation()) {
				case DOWN:
					minY = 15 / 16F;
					break;
				case UP:
					maxY = 1 / 16F;
					break;
				case NORTH:
					minZ = 15 / 16F;
					break;
				case SOUTH:
					maxZ = 1 / 16F;
					break;
				case WEST:
					minX = 15 / 16F;
					break;
				case EAST:
					maxX = 1 / 16F;
					break;
				default:
					break;
				}

			} else if (tile.isStanding()) {
				minX = minZ = maxY = 1 / 16F;
				maxX = maxZ = 15 / 16F;
			} else {
				minX = minZ = 1 / 16F;
				minY = maxX = maxZ = 15 / 16F;
			}

			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}

	}

}

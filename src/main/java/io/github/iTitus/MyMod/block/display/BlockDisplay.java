package io.github.iTitus.MyMod.block.display;

import io.github.iTitus.MyMod.block.EnumBlockType;
import io.github.iTitus.MyMod.block.MyBlock;
import io.github.iTitus.MyMod.tileentity.display.TileEntityDisplay;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDisplay extends MyBlock implements ITileEntityProvider {

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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		TileEntityDisplay display = (TileEntityDisplay) world.getTileEntity(x,
				y, z);
		if (side == display.getOrientation().ordinal())
			if (display.isActive())
				return frontIconOn;
			else
				return frontIconOff;
		else
			return super.getIcon(world, x, y, z, side);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return ((TileEntityDisplay) world.getTileEntity(x, y, z)).isActive() ? 15
				: 0;
	}

	@Override
	public boolean is6Sided() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		frontIconOn = register.registerIcon(getTextureName() + "_frontOn");
		frontIconOff = register.registerIcon(getTextureName() + "_frontOff");
	}

}

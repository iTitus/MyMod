package io.github.iTitus.MyMod.common.block.display;

import io.github.iTitus.MyMod.common.block.EnumBlockType;
import io.github.iTitus.MyMod.common.item.MyItemBlock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockDisplay extends MyItemBlock {

	@SideOnly(Side.CLIENT)
	private IIcon iconStanding, iconHanging;

	public ItemBlockDisplay(Block b) {
		super(b, EnumBlockType.DISPLAY);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean b) {
		super.addInformation(stack, player, list, b);
		list.add("");
		switch (stack.getItemDamage()) {
		case 0:
			list.add("Attaching version");
			break;
		case 1:
			list.add("Free standing version");
			break;
		case 2:
			list.add("Free hanging version");
			break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return iconStanding;
		if (dmg == 2)
			return iconHanging;
		return super.getIconFromDamage(dmg);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		super.registerIcons(register);
		iconStanding = register.registerIcon(field_150939_a.getItemIconName()
				+ "_1");
		iconStanding = register.registerIcon(field_150939_a.getItemIconName()
				+ "_2");
	}

}

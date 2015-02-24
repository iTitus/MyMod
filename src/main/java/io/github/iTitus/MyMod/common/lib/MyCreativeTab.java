package io.github.iTitus.MyMod.common.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.iTitus.MyMod.MyMod;
import io.github.iTitus.MyMod.common.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MyCreativeTab extends CreativeTabs {

    public static final CreativeTabs INSTANCE = new MyCreativeTab();

    private MyCreativeTab() {
        super(MyMod.MOD_ID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModBlocks.zero.getItem(null, 0, 0, 0);
    }

}

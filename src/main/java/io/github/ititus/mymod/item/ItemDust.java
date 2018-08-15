package io.github.ititus.mymod.item;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.api.dust.DustManager;
import io.github.ititus.mymod.api.dust.IDust;
import io.github.ititus.mymod.init.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

import java.util.Comparator;

public class ItemDust extends ItemBase {

    public ItemDust() {
        super(MyMod.DUST);
        setHasSubtypes(true);
        setCreativeTab(ModTabs.DUST_TAB);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        IDust dust = DustManager.dusts.get(stack);
        return dust != null ? super.getUnlocalizedName(stack) + "." + dust.getUnlocalizedName() : super.getUnlocalizedName(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IDust dust = DustManager.dusts.get(stack);
        if (dust != null) {
            String unlocalizedName = getUnlocalizedName(stack) + ".name";
            String specificTranslation = I18n.translateToLocal(unlocalizedName);
            if (!unlocalizedName.equals(specificTranslation)) {
                return specificTranslation;
            }
            String unlocalizedMaterial = "material." + MyMod.MOD_ID + ":" + dust.getUnlocalizedName() + ".name";
            String materialTranslation = I18n.translateToLocal(unlocalizedMaterial);
            return I18n.translateToLocalFormatted(getUnlocalizedName() + ".name", materialTranslation);
        }
        return I18n.translateToLocalFormatted(getUnlocalizedName() + ".name", I18n.translateToLocal("material." + MyMod.MOD_ID + ":unknown.name"));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            DustManager.dusts.getAll().stream().sorted(Comparator.comparingInt(IDust::getID)).forEachOrdered(dust -> items.add(dust.getStack()));
        }
    }
}

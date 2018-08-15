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
    public String getTranslationKey(ItemStack stack) {
        IDust dust = DustManager.dusts.get(stack);
        return dust != null ? super.getTranslationKey(stack) + "." + dust.getTranslationKey() : super.getTranslationKey(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IDust dust = DustManager.dusts.get(stack);
        if (dust != null) {
            String translationKey = getTranslationKey(stack) + ".name";
            String specificTranslation = I18n.translateToLocal(translationKey);
            if (!translationKey.equals(specificTranslation)) {
                return specificTranslation;
            }
            String unlocalizedMaterial = "material." + MyMod.MOD_ID + "." + dust.getTranslationKey() + ".name";
            String materialTranslation = I18n.translateToLocal(unlocalizedMaterial);
            return I18n.translateToLocalFormatted(getTranslationKey() + ".name", materialTranslation);
        }
        return I18n.translateToLocalFormatted(getTranslationKey() + ".name", I18n.translateToLocal("material." + MyMod.MOD_ID + ".unknown.name"));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            DustManager.dusts.getAll().stream().sorted(Comparator.comparingInt(IDust::getID)).forEachOrdered(dust -> items.add(dust.getStack()));
        }
    }
}

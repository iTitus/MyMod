package io.github.ititus.mymod.util.creative.tab;

import io.github.ititus.mymod.MyMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModCreativeTab extends CreativeTabs {

    private final Supplier<ItemStack> iconStackSupplier;
    private boolean hasSearchBar;

    public ModCreativeTab(String label, Supplier<ItemStack> iconStackSupplier) {
        super(MyMod.MOD_ID + "." + label + ".name");
        this.iconStackSupplier = iconStackSupplier;
        this.hasSearchBar = false;
    }

    @Override
    public ItemStack createIcon() {
        return iconStackSupplier.get();
    }

    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }

    public ModCreativeTab enableSearchBar() {
        hasSearchBar = true;
        return this;
    }

    @Override
    public ModCreativeTab setBackgroundImageName(String texture) {
        super.setBackgroundImageName(texture);
        return this;
    }

    public ModCreativeTab setSearchBackground() {
        return setBackgroundImageName("item_search.png");
    }

    public ModCreativeTab setStandardBackground() {
        return setBackgroundImageName("items.png");
    }
}

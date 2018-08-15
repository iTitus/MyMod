package io.github.ititus.mymod.item;

import io.github.ititus.mymod.init.ModTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected final String name;

    public ItemBase(String name) {
        super();
        this.name = name;
        setRegistryName(name);
        setTranslationKey(getRegistryName().toString().replace(':', '.'));
        setCreativeTab(ModTabs.MAIN_TAB);
    }

    public String getName() {
        return name;
    }

}

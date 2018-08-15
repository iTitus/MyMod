package io.github.ititus.mymod.api.dust;

import net.minecraft.item.ItemStack;

public interface IDust {

    String getOreName();

    String getTranslationKey();

    int getID();

    int getColor();

    ItemStack getStack(int amount);

    default String getOreDustName() {
        return "dust" + getOreName();
    }

    default String getOreIngotName() {
        return "ingot" + getOreName();
    }

    default String getOreOreName() {
        return "ore" + getOreName();
    }

    default String getOreBlockName() {
        return "block" + getOreName();
    }

    default ItemStack getStack() {
        return getStack(1);
    }
}

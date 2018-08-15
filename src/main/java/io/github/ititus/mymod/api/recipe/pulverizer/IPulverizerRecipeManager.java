package io.github.ititus.mymod.api.recipe.pulverizer;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IPulverizerRecipeManager {

    IPulverizerRecipe get(ItemStack stack);

    Collection<IPulverizerRecipe> getAll();

    void register(IPulverizerRecipe recipe);

    //TODO: FIXME
    void remove(ItemStack stack);

}

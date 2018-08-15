package io.github.ititus.mymod.api.recipe.pulverizer;

import io.github.ititus.mymod.api.recipe.IRecipeInput;
import io.github.ititus.mymod.api.recipe.IRecipeOutput;
import net.minecraft.item.ItemStack;

public interface IPulverizerRecipe {

    IRecipeInput<ItemStack> getInput();

    IRecipeOutput<ItemStack> getPrimaryOutput();

    IRecipeOutput<ItemStack> getSecondaryOutput();

    int getTime();

    default boolean matches(ItemStack stack) {
        return getInput().matches(stack);
    }

    default boolean isValid() {
        return getInput().isValid() && getPrimaryOutput().isValid() && (getSecondaryOutput() == null || getSecondaryOutput().isValid());
    }
}

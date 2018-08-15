package io.github.ititus.mymod.recipe;

import io.github.ititus.mymod.api.recipe.IRecipeStackOutput;
import net.minecraft.item.ItemStack;

public class StackOutput implements IRecipeStackOutput {

    private final ItemStack stack;

    public StackOutput(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemStack getOutput() {
        return stack;
    }

    @Override
    public boolean isValid() {
        return !getOutput().isEmpty();
    }

}

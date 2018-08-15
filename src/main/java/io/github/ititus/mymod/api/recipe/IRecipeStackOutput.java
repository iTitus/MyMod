package io.github.ititus.mymod.api.recipe;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface IRecipeStackOutput extends IRecipeOutput<ItemStack> {

    ItemStack getOutput();

    @Override
    default ItemStack getOutput(Random r) {
        return getOutput().copy();
    }

    @Override
    default ItemStack getExample() {
        return getOutput();
    }

    @Override
    boolean isValid();
}

package io.github.ititus.mymod.recipe;

import io.github.ititus.mymod.api.recipe.IRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

public class StackInput implements IRecipeInput<ItemStack> {

    private final ItemStack stack;
    private final List<ItemStack> listView;

    public StackInput(ItemStack stack) {
        this.stack = stack.copy();
        this.listView = Collections.singletonList(stack.copy());
    }

    @Override
    public boolean matches(ItemStack stack) {
        return OreDictionary.itemMatches(this.stack, stack, false);
    }

    @Override
    public List<ItemStack> getExamples() {
        return listView;
    }

    @Override
    public boolean isValid() {
        return !stack.isEmpty();
    }
}

package io.github.ititus.mymod.recipe;

import io.github.ititus.mymod.api.recipe.IRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

public class OreDictInput implements IRecipeInput<ItemStack> {

    private final NonNullList<ItemStack> oreStacks;
    private final List<ItemStack> listView;

    public OreDictInput(String oreName) {
        this.oreStacks = OreDictionary.getOres(oreName);
        this.listView = Collections.unmodifiableList(oreStacks);
    }

    @Override
    public boolean matches(ItemStack stack) {
        return OreDictionary.containsMatch(false, oreStacks, stack);
    }

    @Override
    public List<ItemStack> getExamples() {
        return listView;
    }

    @Override
    public boolean isValid() {
        return !oreStacks.isEmpty() && oreStacks.stream().noneMatch(ItemStack::isEmpty);
    }
}

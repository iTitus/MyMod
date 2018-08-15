package io.github.ititus.mymod.recipe.pulverizer;

import com.google.common.collect.Lists;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipeManager;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PulverizerRecipeManager implements IPulverizerRecipeManager {

    private final List<IPulverizerRecipe> recipes = Lists.newArrayList();
    private final Collection<IPulverizerRecipe> recipeView = Collections.unmodifiableCollection(recipes);

    @Override
    public IPulverizerRecipe get(ItemStack stack) {
        return stack.isEmpty() ? null : recipes.stream().filter(recipe -> recipe.isValid() && recipe.matches(stack)).findFirst().orElse(null);
    }

    @Override
    public Collection<IPulverizerRecipe> getAll() {
        return recipeView;
    }

    @Override
    public void register(IPulverizerRecipe recipe) {
        recipes.add(Objects.requireNonNull(recipe));
    }

    //TODO: FIXME
    @Override
    public void remove(ItemStack stack) {
        throw new UnsupportedOperationException();
    }
}

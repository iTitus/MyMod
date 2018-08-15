package io.github.ititus.mymod.recipe.dumper;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface IRecipeDumperHandler {

    Map<String, String> getPropertyMap(IRecipeCategory<? extends IRecipeWrapper> recipeCategory, IRecipeWrapper recipeWrapper, List<String> propertyList);

}

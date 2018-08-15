package io.github.ititus.mymod.recipe.dumper;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;

public interface IRecipeCategoryDumperHandler {

    List<String> getPropertyList(IRecipeCategory<? extends IRecipeWrapper> recipeCategory);

    IRecipeDumperHandler getRecipeDumperHandler(IRecipeCategory<? extends IRecipeWrapper> recipeCategory, IRecipeWrapper recipeWrapper, List<String> propertyList);
}

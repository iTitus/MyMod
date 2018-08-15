package io.github.ititus.mymod.recipe.dumper;

import com.google.common.collect.Maps;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RecipeDumperRegistry {

    private static final Map<Predicate<IRecipeCategory<? extends IRecipeWrapper>>, IRecipeCategoryDumperHandler> HANDLER = Maps.newHashMap();

    public static final IRecipeCategoryDumperHandler DEFAULT_RECIPE_CATEGORY_DUMPER_HANDLER = new DefaultRecipeCategoryDumperHandler();
    public static final IRecipeDumperHandler DEFAULT_RECIPE_DUMPER_HANDLER = (recipeCategory, recipeWrapper, propertyList) -> Collections.emptyMap();

    public static IRecipeCategoryDumperHandler getHandler(IRecipeCategory<? extends IRecipeWrapper> recipeCategory) {
        for (Map.Entry<Predicate<IRecipeCategory<? extends IRecipeWrapper>>, IRecipeCategoryDumperHandler> entry : HANDLER.entrySet()) {
            if (entry.getKey().test(recipeCategory)) {
                return entry.getValue();
            }
        }
        return DEFAULT_RECIPE_CATEGORY_DUMPER_HANDLER;
    }

    public static void addHandler(Predicate<IRecipeCategory<? extends IRecipeWrapper>> predicate, IRecipeCategoryDumperHandler handler) {
        HANDLER.put(predicate, handler);
    }

    private static class DefaultRecipeCategoryDumperHandler implements IRecipeCategoryDumperHandler {

        @Override
        public List<String> getPropertyList(IRecipeCategory<? extends IRecipeWrapper> recipeCategory) {
            return Collections.emptyList();
        }

        @Override
        public IRecipeDumperHandler getRecipeDumperHandler(IRecipeCategory<? extends IRecipeWrapper> recipeCategory, IRecipeWrapper recipeWrapper, List<String> propertyList) {
            return DEFAULT_RECIPE_DUMPER_HANDLER;
        }
    }

}

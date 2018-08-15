package io.github.ititus.mymod.compat.jei;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.api.recipe.RecipeManager;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import io.github.ititus.mymod.client.gui.GuiPulverizer;
import io.github.ititus.mymod.compat.jei.recipe.pulverizer.PulverizerRecipeCategory;
import io.github.ititus.mymod.compat.jei.recipe.pulverizer.PulverizerRecipeWrapper;
import io.github.ititus.mymod.init.ModBlocks;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.stream.Collectors;

@JEIPlugin
public class MyModJEIPlugin implements IModPlugin {

    public static final String PULVERIZER_UID = MyMod.MOD_ID + "." + "pulverizer";
    public static IJeiRuntime jeiRuntime;

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(IPulverizerRecipe.class, recipe -> new PulverizerRecipeWrapper(registry.getJeiHelpers().getStackHelper(), recipe), PULVERIZER_UID);
        if (ModBlocks.pulverizer != null) {
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.pulverizer), PULVERIZER_UID);
        }
        registry.addRecipeClickArea(GuiPulverizer.class, 55, 35, 24, 17, PULVERIZER_UID);
        registry.addRecipes(RecipeManager.pulverizer.getAll().stream().filter(IPulverizerRecipe::isValid).collect(Collectors.toList()), PULVERIZER_UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PulverizerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        MyModJEIPlugin.jeiRuntime = jeiRuntime;
    }
}

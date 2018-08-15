package io.github.ititus.mymod.compat.jei.recipe.pulverizer;

import com.google.common.collect.Lists;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class PulverizerRecipeWrapper implements IRecipeWrapper {

    private final IStackHelper stackHelper;
    private final IPulverizerRecipe recipe;

    public PulverizerRecipeWrapper(IStackHelper stackHelper, IPulverizerRecipe recipe) {
        this.stackHelper = stackHelper;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, Collections.singletonList(stackHelper.getAllSubtypes(recipe.getInput().getExamples())));
        List<ItemStack> list = Lists.newArrayList(recipe.getPrimaryOutput().getExample());
        if (recipe.getSecondaryOutput() != null) {
            list.add(recipe.getSecondaryOutput().getExample());
        }
        ingredients.setOutputs(ItemStack.class, list);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String text = "Time: " + recipe.getTime();
        FontRenderer fr = minecraft.fontRenderer;
        fr.drawString(text, (recipeWidth - fr.getStringWidth(text)) / 2, (recipeHeight / 2) + 4, 0x404040);
    }

    public IPulverizerRecipe getRecipe() {
        return recipe;
    }
}

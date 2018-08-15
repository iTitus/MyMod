package io.github.ititus.mymod.compat.jei.recipe.pulverizer;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.compat.jei.MyModJEIPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class PulverizerRecipeCategory implements IRecipeCategory<PulverizerRecipeWrapper> {

    private final String name;
    private final IDrawable background, slotBackground, arrowStatic, arrow;

    public PulverizerRecipeCategory(IGuiHelper guiHelper) {
        this.name = I18n.translateToLocal("recipe." + getUid() + ".title");
        this.background = guiHelper.createBlankDrawable(96, 36);
        this.slotBackground = guiHelper.getSlotDrawable();

        ResourceLocation furnaceBackground = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        this.arrowStatic = guiHelper.createDrawable(furnaceBackground, 79, 34, 23, 16);
        this.arrow = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(furnaceBackground, 176, 14, 24, 17), 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return MyModJEIPlugin.PULVERIZER_UID;
    }

    @Override
    public String getModName() {
        return MyMod.MOD_NAME;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrowStatic.draw(minecraft, 26, 1);
        arrow.draw(minecraft, 26, 1);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PulverizerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.setBackground(0, slotBackground);

        guiItemStacks.init(1, false, 58, 0);
        guiItemStacks.setBackground(1, slotBackground);
        guiItemStacks.init(2, false, 78, 0);
        guiItemStacks.setBackground(2, slotBackground);

        guiItemStacks.set(ingredients);

        guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

            switch (slotIndex) {
                case 0:
                    recipeWrapper.getRecipe().getInput().addTooltip(ingredient, tooltip);
                    break;
                case 1:
                    recipeWrapper.getRecipe().getPrimaryOutput().addTooltip(ingredient, tooltip);
                    break;
                case 2:
                    if (recipeWrapper.getRecipe().getSecondaryOutput() != null) {
                        recipeWrapper.getRecipe().getSecondaryOutput().addTooltip(ingredient, tooltip);
                    }
                    break;
            }

        });

    }
}

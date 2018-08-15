package io.github.ititus.mymod.recipe;

import io.github.ititus.mymod.api.recipe.IRecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Random;

public class ChancedStackOutput implements IRecipeOutput<ItemStack> {

    private final ItemStack stack;
    private final IOutputSizeDistribution distribution;

    public ChancedStackOutput(ItemStack stack, double chance) {
        this(stack, new IOutputSizeDistribution() {

            @Override
            public int get(Random r) {
                return ((int) chance) + (r.nextDouble() < (chance - ((int) chance)) ? 1 : 0);
            }

            @Override
            public int getMax() {
                return MathHelper.ceil(chance);
            }

            @Override
            public boolean isValid() {
                return getMax() > 0;
            }

            @Override
            public void addTooltip(ItemStack ingredient, List<String> tooltip) {
                int percentChance = (int) (100 * chance);
                String percentString = (percentChance == 0 ? "< 1" : percentChance) + "%";
                tooltip.add(I18n.translateToLocalFormatted("text.mymod:chance", percentString));
            }
        });
    }

    public ChancedStackOutput(ItemStack stack, IOutputSizeDistribution distribution) {
        this.stack = stack;
        this.stack.setCount(distribution.getMax());
        this.distribution = distribution;
    }

    @Override
    public ItemStack getOutput(Random r) {
        return ItemHandlerHelper.copyStackWithSize(stack, distribution.get(r));
    }

    @Override
    public void addTooltip(ItemStack ingredient, List<String> tooltip) {
        distribution.addTooltip(ingredient, tooltip);
    }

    @Override
    public ItemStack getExample() {
        return stack;
    }

    @Override
    public boolean isValid() {
        return !stack.isEmpty() && distribution.isValid();
    }

    public interface IOutputSizeDistribution {

        int get(Random r);

        int getMax();

        boolean isValid();

        void addTooltip(ItemStack ingredient, List<String> tooltip);

    }
}

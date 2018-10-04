package io.github.ititus.mymod.recipe.pulverizer;

import io.github.ititus.mymod.api.recipe.IRecipeInput;
import io.github.ititus.mymod.api.recipe.IRecipeOutput;
import io.github.ititus.mymod.api.recipe.pulverizer.IPulverizerRecipe;
import io.github.ititus.mymod.recipe.ChancedStackOutput;
import io.github.ititus.mymod.recipe.OreDictInput;
import io.github.ititus.mymod.recipe.StackInput;
import io.github.ititus.mymod.recipe.StackOutput;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PulverizerRecipe implements IPulverizerRecipe {

    public static final int DEFAULT_TIME = 200;

    private final IRecipeInput<ItemStack> input;
    private final IRecipeOutput<ItemStack> primaryOutput, secondaryOutput;
    private final int time;

    public PulverizerRecipe(IRecipeInput<ItemStack> input, IRecipeOutput<ItemStack> primaryOutput, IRecipeOutput<ItemStack> secondaryOutput, int time) {
        this.input = Objects.requireNonNull(input);
        this.primaryOutput = Objects.requireNonNull(primaryOutput);
        this.secondaryOutput = secondaryOutput;
        this.time = time;
    }

    public static PulverizerRecipeBuilder builder() {
        return new PulverizerRecipeBuilder();
    }

    @Override
    public IRecipeInput<ItemStack> getInput() {
        return input;
    }

    @Nonnull
    @Override
    public IRecipeOutput<ItemStack> getPrimaryOutput() {
        return primaryOutput;
    }

    @Nullable
    @Override
    public IRecipeOutput<ItemStack> getSecondaryOutput() {
        return secondaryOutput;
    }

    @Override
    public int getTime() {
        return time;
    }

    public static class PulverizerRecipeBuilder {

        private IRecipeInput<ItemStack> input;
        private IRecipeOutput<ItemStack> primaryOutput, secondaryOutput;
        private int time;

        public PulverizerRecipeBuilder() {
            this.time = DEFAULT_TIME;
        }

        public PulverizerRecipeBuilder in(String oreName) {
            this.input = new OreDictInput(oreName);
            return this;
        }

        public PulverizerRecipeBuilder in(ItemStack stack) {
            this.input = new StackInput(stack);
            return this;
        }

        public PulverizerRecipeBuilder out1(ItemStack stack) {
            this.primaryOutput = new StackOutput(stack);
            return this;
        }

        public PulverizerRecipeBuilder out1(ItemStack stack, double chance) {
            this.primaryOutput = new ChancedStackOutput(stack, chance);
            return this;
        }

        public PulverizerRecipeBuilder out2(ItemStack stack) {
            this.secondaryOutput = new StackOutput(stack);
            return this;
        }

        public PulverizerRecipeBuilder out2(ItemStack stack, double chance) {
            int chanceInt = (int) chance;
            if (chance == chanceInt) {
                ItemStack copy = stack.copy();
                copy.setCount(chanceInt);
                this.secondaryOutput = new StackOutput(copy);
            } else {
                this.secondaryOutput = new ChancedStackOutput(stack, chance);
            }
            return this;
        }

        public PulverizerRecipeBuilder time(int time) {
            this.time = time;
            return this;
        }

        public PulverizerRecipe build() {
            return new PulverizerRecipe(input, primaryOutput, secondaryOutput, time);
        }

    }

}

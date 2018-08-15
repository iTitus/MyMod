package io.github.ititus.mymod.api.recipe;

import java.util.List;
import java.util.Random;

public interface IRecipeOutput<T> {

    T getOutput(Random r);

    T getExample();

    boolean isValid();

    default void addTooltip(T ingredient, List<String> tooltip) {
    }

}

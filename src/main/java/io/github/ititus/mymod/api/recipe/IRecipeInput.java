package io.github.ititus.mymod.api.recipe;

import java.util.List;

public interface IRecipeInput<T> {

    boolean matches(T t);

    List<T> getExamples();

    boolean isValid();

    default int getAmount() {
        return 1;
    }

    default void addTooltip(T input, List<String> tooltip) {
    }

}

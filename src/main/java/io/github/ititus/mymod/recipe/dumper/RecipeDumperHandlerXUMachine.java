package io.github.ititus.mymod.recipe.dumper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.rwtema.extrautils2.api.machine.*;
import com.rwtema.extrautils2.crafting.jei.JEIMachine;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.TObjectIntMap;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RecipeDumperHandlerXUMachine implements IRecipeCategoryDumperHandler, IRecipeDumperHandler {

    private static final int PREFIX_LENGTH = "xu2_machine_".length();

    @Override
    public List<String> getPropertyList(IRecipeCategory<? extends IRecipeWrapper> recipeCategory) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        Machine machine = MachineRegistry.getMachine(recipeCategory.getUid().substring(PREFIX_LENGTH));

        IntStream.range(0, machine.itemInputs.size()).forEachOrdered(i -> builder.add("itemInput" + i, "itemInputAmount" + i, "containerItem" + i));
        IntStream.range(0, machine.fluidInputs.size()).forEachOrdered(i -> builder.add("fluidInput" + i, "fluidInputAmount" + i));

        IntStream.range(0, machine.itemOutputs.size()).forEachOrdered(i -> builder.add("itemOutput" + i, "probabilityModifierItemOutput" + i));
        IntStream.range(0, machine.fluidOutputs.size()).forEachOrdered(i -> builder.add("fluidOutput" + i, "probabilityModifierFluidOutput" + i));

        builder.add("energyOutput", "processingTime", "energyRate");

        return builder.build();
    }

    @Override
    public IRecipeDumperHandler getRecipeDumperHandler(IRecipeCategory<? extends IRecipeWrapper> recipeCategory, IRecipeWrapper recipeWrapper, List<String> propertyList) {
        return this;
    }

    @Override
    public Map<String, String> getPropertyMap(IRecipeCategory<? extends IRecipeWrapper> recipeCategory, IRecipeWrapper recipeWrapper, List<String> propertyList) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        JEIMachine.JEIMachineRecipe.Wrapper wrapper = (JEIMachine.JEIMachineRecipe.Wrapper) recipeWrapper;
        JEIMachine.JEIMachineRecipe jeiRecipe = wrapper.parentRecipe;
        IMachineRecipe recipe = jeiRecipe.recipe;

        Map<MachineSlotItem, ItemStack> itemStacks = Maps.newHashMap();
        for (MachineSlotItem slot : jeiRecipe.machine.itemInputs) {
            List<ItemStack> list = jeiRecipe.inputs.get(slot);
            if (list.size() > 1) {
                System.out.println(recipeCategory.getUid() + ": >1 input " + slot + " -> " + list);
            }
            itemStacks.put(slot, list.get(0));
        }

        Map<MachineSlotFluid, FluidStack> fluidStacks = Maps.newHashMap();
        for (MachineSlotFluid slot : jeiRecipe.machine.fluidInputs) {
            List<FluidStack> list = jeiRecipe.fluids.get(slot);
            if (list.size() > 1) {
                System.out.println(recipeCategory.getUid() + ": >1 input " + slot + " -> " + list);
            }
            fluidStacks.put(slot, list.get(0));
        }

        if (!recipe.matches(itemStacks, fluidStacks)) {
            System.out.println(recipeCategory.getUid() + ": given inputs don't match recipe " + itemStacks + " + " + fluidStacks);
            return Collections.emptyMap();
        }

        TObjectIntMap<MachineSlot> amounts = recipe.getAmountToConsume(itemStacks, fluidStacks);
        Map<MachineSlotItem, ItemStack> containerItems = recipe.getContainerItems(itemStacks, fluidStacks);
        TObjectFloatMap<MachineSlot> probabilityModifier = recipe.getProbabilityModifier(itemStacks, fluidStacks);

        IntStream.range(0, jeiRecipe.machine.itemInputs.size()).forEach(i -> {
            MachineSlotItem slot = jeiRecipe.machine.itemInputs.get(i);
            builder.put("itemInput" + i, "" + itemStacks.get(slot));
            builder.put("itemInputAmount" + i, "" + amounts.get(slot));
            builder.put("containerItem" + i, "" + containerItems.get(slot));
        });

        IntStream.range(0, jeiRecipe.machine.fluidInputs.size()).forEach(i -> {
            MachineSlotFluid slot = jeiRecipe.machine.fluidInputs.get(i);
            builder.put("fluidInput" + i, "" + fluidStacks.get(slot));
            builder.put("fluidInputAmount" + i, "" + amounts.get(slot));
        });

        IntStream.range(0, jeiRecipe.machine.itemOutputs.size()).forEach(i -> {
            MachineSlotItem slot = jeiRecipe.machine.itemOutputs.get(i);
            ItemStack stack = recipe.getItemOutputs(itemStacks, fluidStacks).get(slot);
            builder.put("itemOutput" + i, "" + stack);
            builder.put("probabilityModifierItemOutput" + i, "" + (probabilityModifier != null && probabilityModifier.containsKey(slot) ? probabilityModifier.get(slot) : 1F));
        });

        IntStream.range(0, jeiRecipe.machine.fluidOutputs.size()).forEach(i -> {
            MachineSlotFluid slot = jeiRecipe.machine.fluidOutputs.get(i);
            FluidStack stack = recipe.getFluidOutputs(itemStacks, fluidStacks).get(slot);
            builder.put("fluidOutput" + i, "" + stack);
            builder.put("probabilityModifierFluidOutput" + i, "" + (probabilityModifier != null && probabilityModifier.containsKey(slot) ? probabilityModifier.get(slot) : 1F));
        });

        builder.put("energyOutput", "" + recipe.getEnergyOutput(itemStacks, fluidStacks));
        builder.put("processingTime", "" + recipe.getProcessingTime(itemStacks, fluidStacks));
        builder.put("energyRate", "" + recipe.getEnergyRate(itemStacks, fluidStacks));

        return builder.build();
    }

}

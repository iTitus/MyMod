package io.github.ititus.mymod.dust;

import com.google.common.base.Strings;
import io.github.ititus.mymod.api.dust.DustManager;
import io.github.ititus.mymod.api.dust.IDust;
import io.github.ititus.mymod.api.recipe.RecipeManager;
import io.github.ititus.mymod.recipe.pulverizer.PulverizerRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;

public class Dusts {

    public static void init() {
        Arrays.stream(EnumMetalDust.values()).forEachOrdered(metalDust -> registerStandardMetalDust(metalDust, true));
        Arrays.stream(EnumDust.values()).forEachOrdered(dust -> registerDust(dust, true));

        Arrays.stream(EnumMetalDust.values()).forEachOrdered(metalDust -> registerStandardMetalDust(metalDust, false));
        Arrays.stream(EnumDust.values()).forEachOrdered(dust -> registerDust(dust, false));
    }

    private static void registerStandardMetalDust(EnumMetalDust metalDust, boolean registration) {
        if (registration) {
            DustManager.dusts.register(new Dust(metalDust.translationKey, metalDust.oreName, metalDust.id, metalDust.color));
        } else {
            IDust dust = DustManager.dusts.get(metalDust.translationKey);
            if (dust != null) {
                PulverizerRecipe.PulverizerRecipeBuilder recipeBuilder = PulverizerRecipe.builder().in(dust.getOreOreName()).out1(dust.getStack(2));
                IDust secondOutputDust = DustManager.dusts.get(metalDust.secondOutputDust);
                ItemStack secondOutputStack = secondOutputDust != null ? secondOutputDust.getStack() : metalDust.secondOutputStack;
                if (!secondOutputStack.isEmpty()) {
                    recipeBuilder = recipeBuilder.out2(secondOutputDust.getStack(), metalDust.secondOutputChance);
                }
                RecipeManager.pulverizer.register(recipeBuilder.build());

                RecipeManager.pulverizer.register(PulverizerRecipe.builder().in(dust.getOreIngotName()).out1(dust.getStack()).build());
                RecipeManager.pulverizer.register(PulverizerRecipe.builder().in(dust.getOreBlockName()).out1(dust.getStack(9)).build());
                NonNullList<ItemStack> stacks = OreDictionary.getOres(dust.getOreIngotName());
                if (!stacks.isEmpty()) {
                    FurnaceRecipes.instance().addSmeltingRecipe(dust.getStack(), stacks.get(0), 0.35F);
                }
            }
        }
    }

    private static void registerDust(EnumDust enumDust, boolean registration) {
        if (registration) {
            DustManager.dusts.register(new Dust(enumDust.translationKey, enumDust.oreName, enumDust.id, enumDust.color));
        } else {
            IDust dust = DustManager.dusts.get(enumDust.translationKey);
            if (dust != null) {
                PulverizerRecipe.PulverizerRecipeBuilder recipeBuilder = PulverizerRecipe.builder();
                if (Strings.isNullOrEmpty(enumDust.oreInput)) {
                    recipeBuilder = recipeBuilder.in(enumDust.stackInput);
                } else {
                    recipeBuilder = recipeBuilder.in(enumDust.oreInput);
                }
                RecipeManager.pulverizer.register(recipeBuilder.out1(dust.getStack(enumDust.outputAmount)).build());
            }
        }
    }

    enum EnumMetalDust {
        IRON(0, "iron", 0xD2CEC9, "nickel", 0.1F),
        GOLD(1, "gold", 0xF8DF17, "copper", 0.1F),
        COPPER(10, "copper", 0xBF5E1F, "gold", 0.1F),
        TIN(11, "tin", 0xB3B3B3, "iron", 0.1F),
        LEAD(12, "lead", 0x808096, "silver", 0.1F),
        SILVER(13, "silver", 0xE0E0E0, "lead", 0.1F),
        NICKEL(14, "nickel", 0xF7F7C7, "platinum", 0.1F),
        PLATINUM(15, "platinum", 0xCCFFFF, "iridium", 0.05F),
        IRIDIUM(16, "iridium", 0xF0EDF5, "platinum", 0.1F),
        MITHRIL(17, "mithril", 0x85D7EF, "gold", 0.1F);

        int id, color;
        String translationKey, oreName, secondOutputDust;
        float secondOutputChance;
        ItemStack secondOutputStack;

        EnumMetalDust(int id, String translationKey, int color, String secondOutputDust, float secondOutputChance) {
            this(id, translationKey, WordUtils.capitalize(translationKey), color, secondOutputDust, ItemStack.EMPTY, secondOutputChance);
        }

        EnumMetalDust(int id, String translationKey, String oreName, int color, String secondOutputDust, ItemStack secondOutputStack, float secondOutputChance) {
            this.id = id;
            this.translationKey = translationKey;
            this.oreName = oreName;
            this.color = color;
            this.secondOutputDust = secondOutputDust;
            this.secondOutputStack = secondOutputStack;
            this.secondOutputChance = secondOutputChance;
        }
    }

    enum EnumDust {
        COAL(2, "coal", 0x1B1B1B, new ItemStack(Items.COAL)),
        CHARCOAL(3, "charcoal", 0x53493A, new ItemStack(Items.COAL, 1, 1)),
        DIAMOND(4, "diamond", 0x13ECFC, "gemDiamond"),
        EMERALD(5, "emerald", 0x00B038, "gemEmerald"),
        LAPIS_LAZULI(6, "lapis", 0x224BAF, "gemLapis"),
        OBSIDIAN(7, "obsidian", 0x171124, "obsidian", 2),
        ENDER_PEARL(8, "enderpearl", 0x105E51, "enderpearl"),
        NETHER_QUARTZ(9, "quartz", 0xDBCCBF, "gemQuartz");

        int id, color, outputAmount;
        String translationKey, oreName;
        String oreInput;
        ItemStack stackInput;

        EnumDust(int id, String translationKey, int color, String oreInput) {
            this(id, translationKey, color, oreInput, 1);
        }

        EnumDust(int id, String translationKey, int color, String oreInput, int outputAmount) {
            this(id, translationKey, color, oreInput, ItemStack.EMPTY, outputAmount);
        }

        EnumDust(int id, String translationKey, int color, ItemStack stackInput) {
            this(id, translationKey, color, stackInput, 1);
        }

        EnumDust(int id, String translationKey, int color, ItemStack stackInput, int outputAmount) {
            this(id, translationKey, color, null, stackInput, outputAmount);
        }

        EnumDust(int id, String translationKey, int color, String oreInput, ItemStack stackInput, int outputAmount) {
            this(id, translationKey, WordUtils.capitalize(translationKey), color, oreInput, stackInput, outputAmount);
        }

        EnumDust(int id, String translationKey, String oreName, int color, String oreInput, ItemStack stackInput, int outputAmount) {
            this.id = id;
            this.translationKey = translationKey;
            this.oreName = oreName;
            this.color = color;
            this.oreInput = oreInput;
            this.stackInput = stackInput;
            this.outputAmount = outputAmount;
        }
    }

}

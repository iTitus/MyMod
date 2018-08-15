package io.github.ititus.mymod.dust;

import io.github.ititus.mymod.api.dust.IDust;
import io.github.ititus.mymod.init.ModItems;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

public class Dust implements IDust {

    protected final String unlocalizedName, oreName;
    protected final int id;
    protected final int color;

    public Dust(String unlocalizedName, int id, int color) {
        this(unlocalizedName, WordUtils.capitalize(unlocalizedName), id, color);
    }

    public Dust(String unlocalizedName, String oreName, int id, int color) {
        this.unlocalizedName = unlocalizedName;
        this.oreName = oreName;
        this.id = id;
        this.color = color;
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public String getOreName() {
        return oreName;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public ItemStack getStack(int amount) {
        if (ModItems.dust != null) {
            return new ItemStack(ModItems.dust, amount, id);
        }
        return ItemStack.EMPTY;
    }
}

package io.github.ititus.mymod.fluid;

import io.github.ititus.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidBase extends Fluid {

    public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
    }

    @Override
    public String getUnlocalizedName() {
        return "fluid." + MyMod.MOD_ID + "." + unlocalizedName + ".name";
    }
}

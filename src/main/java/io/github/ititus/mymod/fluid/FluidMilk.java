package io.github.ititus.mymod.fluid;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.init.ModBlocks;
import net.minecraft.util.ResourceLocation;

public class FluidMilk extends FluidBase {

    private static final ResourceLocation STILL = new ResourceLocation(MyMod.MOD_ID, "blocks/" + MyMod.MILK + "_still");
    private static final ResourceLocation FLOWING = new ResourceLocation(MyMod.MOD_ID, "blocks/" + MyMod.MILK + "_flow");

    public FluidMilk() {
        super(MyMod.MILK, STILL, FLOWING);
        setBlock(ModBlocks.blockMilk);
    }
}

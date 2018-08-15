package io.github.ititus.mymod.block;

import io.github.ititus.mymod.init.ModFluids;
import net.minecraft.block.material.Material;

public class BlockFluidMilk extends BlockFluidBase {

    public BlockFluidMilk() {
        super(ModFluids.fluidMilk, Material.WATER);
    }
}

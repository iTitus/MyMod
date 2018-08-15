package io.github.ititus.mymod.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBase extends BlockFluidClassic {

    public BlockFluidBase(Fluid fluid, Material material) {
        super(fluid, material);
        setRegistryName(fluid.getName());
        setTranslationKey(getRegistryName().toString().replace(':', '.'));
    }

}

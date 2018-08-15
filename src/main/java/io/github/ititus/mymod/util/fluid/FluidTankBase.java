package io.github.ititus.mymod.util.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class FluidTankBase extends FluidTank {

    public FluidTankBase(int capacity) {
        super(capacity);
    }

    public FluidTankBase(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FluidTankBase(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    public FluidTankBase(TileEntity tile, int capacity) {
        this(capacity);
        setTileEntity(tile);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return canDrainFluidType(getFluid()) ? drainInternal(maxDrain, doDrain) : null;
    }

    @Override
    protected void onContentsChanged() {
        if (tile != null) {
            tile.markDirty();
        }
    }

    public int getFreeSpace() {
        return getCapacity() - getFluidAmount();
    }
}

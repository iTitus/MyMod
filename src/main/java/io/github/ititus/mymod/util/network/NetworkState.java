package io.github.ititus.mymod.util.network;

import io.github.ititus.mymod.util.side.SideConfiguration;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Arrays;
import java.util.Objects;

public class NetworkState {

    private final int[] ints;
    private final FluidStack[] fluidStacks;
    private final SideConfiguration[] sideConfigurations;

    public NetworkState(int ints, int fluidStacks, int sideConfigurations) {
        this.ints = new int[ints];
        this.fluidStacks = new FluidStack[fluidStacks];
        this.sideConfigurations = new SideConfiguration[sideConfigurations];
    }

    public static void toBytes(ByteBuf buf, NetworkState state) {
        buf.writeInt(state.getInts());
        buf.writeInt(state.getFluidStacks());
        buf.writeInt(state.getSideConfigurations());
        state.toBytes0(buf);
    }

    public static NetworkState fromBytes(ByteBuf buf) {
        NetworkState state = new NetworkState(buf.readInt(), buf.readInt(), buf.readInt());
        state.fromBytes0(buf);
        return state;
    }

    private void toBytes0(ByteBuf buf) {
        for (int i : ints) {
            buf.writeInt(i);
        }
        for (FluidStack fluid : fluidStacks) {
            if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
                buf.writeInt(-1);
            } else {
                buf.writeInt(fluid.amount);
                ByteBufUtils.writeUTF8String(buf, fluid.getFluid().getName());
                ByteBufUtils.writeTag(buf, fluid.tag);
            }
        }
        for (SideConfiguration sideConfiguration : sideConfigurations) {
            SideConfiguration.toBytes(buf, sideConfiguration);
        }
    }

    private void fromBytes0(ByteBuf buf) {
        for (int i = 0; i < ints.length; i++) {
            ints[i] = buf.readInt();
        }
        for (int i = 0; i < fluidStacks.length; i++) {
            FluidStack fluid = null;
            int amount = buf.readInt();
            if (amount > 0) {
                fluid = FluidRegistry.getFluidStack(ByteBufUtils.readUTF8String(buf), amount);
                NBTTagCompound tag = ByteBufUtils.readTag(buf);
                if (fluid != null) {
                    fluid.tag = tag;
                }
            }
            fluidStacks[i] = fluid;
        }
        for (int i = 0; i < sideConfigurations.length; i++) {
            sideConfigurations[i] = SideConfiguration.fromBytes(buf);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof NetworkState)) {
            return false;
        }
        NetworkState that = (NetworkState) o;
        if (!Arrays.equals(ints, that.ints)) {
            return false;
        }
        if (fluidStacks != that.fluidStacks) {
            if (fluidStacks.length != that.fluidStacks.length) {
                return false;
            }
            for (int i = 0; i < fluidStacks.length; i++) {
                FluidStack fluid1 = fluidStacks[i];
                FluidStack fluid2 = that.fluidStacks[i];
                if (fluid1 != fluid2) {
                    if (fluid1 == null || fluid2 == null || !fluid1.isFluidStackIdentical(fluid2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ints, fluidStacks);
    }

    public void set(NetworkState state) {
        for (int i = 0; i < ints.length; i++) {
            ints[i] = state.ints[i];
        }
        for (int i = 0; i < fluidStacks.length; i++) {
            FluidStack fluid = state.fluidStacks[i];
            fluidStacks[i] = fluid != null ? fluid.copy() : null;
        }
    }

    public int getInt(int i) {
        return ints[i];
    }

    public FluidStack getFluidStack(int i) {
        return fluidStacks[i];
    }

    public SideConfiguration getSideConfiguration(int i) {
        return sideConfigurations[i];
    }

    public void set(int i, int value) {
        ints[i] = value;
    }

    public void set(int i, FluidStack value) {
        fluidStacks[i] = value;
    }

    public void set(int i, SideConfiguration value) {
        sideConfigurations[i] = value;
    }

    public int getInts() {
        return ints.length;
    }

    public int getFluidStacks() {
        return fluidStacks.length;
    }

    public int getSideConfigurations() {
        return sideConfigurations.length;
    }
}

package io.github.ititus.mymod.compat.top;

import mcjty.theoneprobe.api.ITheOneProbe;

import java.util.function.Function;

public class MyModTOPCompat implements Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe probe) {
        System.out.println("Loading MyModTOPCompat");
        return null;
    }
}

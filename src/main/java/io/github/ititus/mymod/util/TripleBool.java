package io.github.ititus.mymod.util;

import net.minecraft.util.math.MathHelper;

public enum TripleBool implements ICyclable<TripleBool> {

    DEFAULT(0, null), TRUE(1, Boolean.TRUE), FALSE(2, Boolean.FALSE);

    public static final TripleBool[] VALUES;

    static {
        TripleBool[] values = values();
        VALUES = new TripleBool[values.length];
        for (int i = 0; i < values.length; i++) {
            TripleBool mode = values[i];
            VALUES[mode.index] = mode;
        }
    }

    private final int index;
    private final Boolean bool;

    TripleBool(int index, Boolean bool) {
        this.index = index;
        this.bool = bool;
    }

    public static TripleBool get(int i) {
        return VALUES[MathHelper.clamp(i, 0, VALUES.length - 1)];
    }

    public static TripleBool get(boolean b) {
        return b ? TRUE : FALSE;
    }

    public static TripleBool get(Boolean b) {
        return b == null ? DEFAULT : b.booleanValue() ? TRUE : FALSE;
    }

    public int getIndex() {
        return index;
    }

    public Boolean get() {
        return bool;
    }

    @Override
    public TripleBool next(int i) {
        int next = index + i;
        while (next >= VALUES.length) {
            next -= VALUES.length;
        }
        return get(next);
    }

    @Override
    public TripleBool prev(int i) {
        int prev = index - i;
        while (prev < 0) {
            prev += VALUES.length;
        }
        return get(prev);
    }

}

package io.github.ititus.mymod.util.side;

import io.github.ititus.mymod.util.ICyclable;
import net.minecraft.util.math.MathHelper;

public enum EnumConfigurationMode implements ICyclable<EnumConfigurationMode> {

    NONE(0), INPUT(1), OUTPUT(2), IO(3);

    public static final EnumConfigurationMode[] VALUES;

    static {
        EnumConfigurationMode[] values = values();
        VALUES = new EnumConfigurationMode[values.length];
        for (int i = 0; i < values.length; i++) {
            EnumConfigurationMode mode = values[i];
            VALUES[mode.index] = mode;
        }
    }

    private final int index;

    EnumConfigurationMode(int index) {
        this.index = index;
    }

    public static EnumConfigurationMode get(int i) {
        return VALUES[MathHelper.clamp(i, 0, VALUES.length - 1)];
    }

    public int getIndex() {
        return index;
    }

    @Override
    public EnumConfigurationMode next(int i) {
        int next = index + i;
        while (next >= VALUES.length) {
            next -= VALUES.length;
        }
        return get(next);
    }

    @Override
    public EnumConfigurationMode prev(int i) {
        int prev = index - i;
        while (prev < 0) {
            prev += VALUES.length;
        }
        return get(prev);
    }

}

package io.github.iTitus.MyMod.common.item;

import io.github.iTitus.MyMod.common.lib.LibNames;

public enum EnumItemType {

    AMMO(LibNames.AMMO_NAME, true, true), DEBUG(LibNames.DEBUG_NAME, true, true), GUN(
            LibNames.GUN_NAME, true, false);

    public final String name;
    public final boolean putInTab, hasTexture;

    private EnumItemType(String name, boolean putinTab, boolean hasTexture) {
        this.name = name;
        this.putInTab = putinTab;
        this.hasTexture = hasTexture;
    }

}

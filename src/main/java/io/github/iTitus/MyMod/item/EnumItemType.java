package io.github.iTitus.MyMod.item;

import io.github.iTitus.MyMod.lib.LibNames;

public enum EnumItemType {

	debug(LibNames.DEBUG_NAME, true, true), gun(LibNames.GUN_NAME, true, false), ammo(
			LibNames.AMMO_NAME, true, true);

	public final String name;
	public final boolean putInTab, hasTexture;

	private EnumItemType(String name, boolean putinTab, boolean hasTexture) {
		this.name = name;
		this.putInTab = putinTab;
		this.hasTexture = hasTexture;
	}

}

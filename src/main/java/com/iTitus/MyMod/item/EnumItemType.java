package com.iTitus.MyMod.item;

import com.iTitus.MyMod.lib.LibNames;

public enum EnumItemType {

	debug(LibNames.DEBUG_NAME), gun(LibNames.GUN_NAME), ammo(LibNames.AMMO_NAME);

	public final String name;

	private EnumItemType(String name) {
		this.name = name;
	}

}

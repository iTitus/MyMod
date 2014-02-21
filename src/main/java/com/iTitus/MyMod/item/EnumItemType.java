package com.iTitus.MyMod.item;

import com.iTitus.MyMod.lib.LibNames;

public enum EnumItemType {

	debug(LibNames.DEBUG_NAME);

	public final String name;

	private EnumItemType(String name) {
		this.name = name;
	}

}

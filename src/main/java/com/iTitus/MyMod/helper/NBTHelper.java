package com.iTitus.MyMod.helper;

import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

	/**
	 * @param nbt
	 *            A <tt>NBTTagCompound</tt> to read
	 * @return
	 */
	public static String readNBT(NBTTagCompound nbt) {
		String s = nbt.toString();

		s = s.replaceAll(",", ", ");
		s = s.replaceAll(":", ": ");

		return s;
	}

}

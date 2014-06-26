package io.github.iTitus.MyMod.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

	public static boolean hasNBT(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound() != null;
	}

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

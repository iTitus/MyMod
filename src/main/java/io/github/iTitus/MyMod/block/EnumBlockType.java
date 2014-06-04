package io.github.iTitus.MyMod.block;

import io.github.iTitus.MyMod.lib.LibNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public enum EnumBlockType {
	TIMESHIFTER(LibNames.TIMESHIFTER, Material.wood, 2F, 5F,
			Block.soundTypeWood), WHEEL(LibNames.WHEEL_NAME, Material.circuits,
			3.5F, 6F, Block.soundTypeStone, 0, 0, true), ZERO(
			LibNames.ZERO_NAME, Material.glass, 2F, 5F, Block.soundTypeGlass,
			0, 0, true), SPHERE(LibNames.SPHERE, Material.rock, 4F, 6F,
			Block.soundTypeStone, 0, 0, true);

	public final float hardness, resistance;
	public final int lightLevel, lightOpacity;
	public final Material material;
	public final String name;
	public final Block.SoundType soundType;
	public final boolean putInTab;

	private EnumBlockType(String name, Material material, float hardness,
			float resistance, Block.SoundType soundType) {
		this.name = name;
		this.material = material;
		this.hardness = hardness;
		this.resistance = resistance;
		this.soundType = soundType;
		this.lightLevel = 0;
		this.lightOpacity = 256;
		this.putInTab = true;
	}

	private EnumBlockType(String name, Material material, float hardness,
			float resistance, Block.SoundType soundType, int lightLevel,
			int lightOpacity, boolean putInTab) {
		this.name = name;
		this.material = material;
		this.hardness = hardness;
		this.resistance = resistance;
		this.soundType = soundType;
		this.lightLevel = lightLevel;
		this.lightOpacity = lightOpacity;
		this.putInTab = putInTab;
	}

}

package io.github.ititus.mymod.block;

import io.github.ititus.mymod.init.ModTabs;
import io.github.ititus.mymod.item.block.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBase extends Block {

    protected final String name;

    public BlockBase(String name) {
        this(name, Material.IRON);
    }

    public BlockBase(String name, Material material) {
        this(name, material, material == Material.CLOTH ? SoundType.CLOTH : material == Material.WOOD ? SoundType.WOOD : material == Material.GLASS ? SoundType.GLASS : SoundType.METAL);
    }

    public BlockBase(String name, Material material, SoundType soundType) {
        super(material);
        this.name = name;
        setRegistryName(name);
        setTranslationKey(getRegistryName().toString().replace(':', '.'));
        setSoundType(soundType);
        setHardness(5);
        setResistance(7);
        setCreativeTab(ModTabs.MAIN_TAB);
    }

    public String getName() {
        return name;
    }

    public Item getItemBlock() {
        return new ItemBlockBase(this);
    }

}

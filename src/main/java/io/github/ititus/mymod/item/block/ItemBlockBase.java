package io.github.ititus.mymod.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    @Override
    public int getMetadata(ItemStack stack) {
        return super.getMetadata(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}

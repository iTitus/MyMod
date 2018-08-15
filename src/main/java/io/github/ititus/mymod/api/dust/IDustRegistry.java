package io.github.ititus.mymod.api.dust;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IDustRegistry {

    void register(IDust dust);

    IDust get(int id);

    IDust get(String name);

    IDust get(ItemStack stack);

    Collection<IDust> getAll();

    //TODO: FIXME
    void remove(int id);

    //TODO: FIXME
    void remove(String name);

    default ItemStack getStack(IDust dust) {
        return getStack(dust, 1);
    }

    default ItemStack getStack(IDust dust, int amount) {
        return dust != null ? dust.getStack(amount) : ItemStack.EMPTY;
    }

    default ItemStack getStack(int id) {
        return getStack(id, 1);
    }

    default ItemStack getStack(int id, int amount) {
        return getStack(get(id), amount);
    }

    default ItemStack getStack(String name) {
        return getStack(name, 1);
    }

    default ItemStack getStack(String name, int amount) {
        return getStack(get(name), amount);
    }

}

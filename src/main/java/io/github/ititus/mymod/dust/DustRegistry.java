package io.github.ititus.mymod.dust;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.github.ititus.mymod.api.dust.IDust;
import io.github.ititus.mymod.api.dust.IDustRegistry;
import io.github.ititus.mymod.item.ItemDust;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DustRegistry implements IDustRegistry {

    private final List<IDust> dusts = Lists.newArrayList();
    private final Collection<IDust> dustView = Collections.unmodifiableCollection(dusts);

    @Override
    public void register(IDust dust) {
        if (dust == null || Strings.isNullOrEmpty(dust.getOreName()) || Strings.isNullOrEmpty(dust.getTranslationKey()) || dust.getID() < 0 || dust.getID() >= Short.MAX_VALUE || get(dust.getID()) != null || get(dust.getOreName()) != null) {
            throw new IllegalArgumentException();
        }
        dusts.add(dust);
        OreDictionary.registerOre(dust.getOreDustName(), dust.getStack());
    }

    @Override
    public IDust get(int id) {
        return id > 0 || id < Short.MAX_VALUE ? dusts.stream().filter(dust -> dust.getID() == id).findFirst().orElse(null) : null;
    }

    @Override
    public IDust get(String name) {
        return Strings.isNullOrEmpty(name) ? null : dusts.stream().filter(dust -> dust.getOreName().equals(name) || dust.getTranslationKey().equals(name)).findFirst().orElse(null);
    }

    @Override
    public IDust get(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ItemDust ? get(stack.getMetadata()) : null;
    }

    @Override
    public Collection<IDust> getAll() {
        return dustView;
    }

    //TODO: FIXME
    @Override
    public void remove(int id) {
        throw new UnsupportedOperationException();
    }

    //TODO: FIXME
    @Override
    public void remove(String name) {
        throw new UnsupportedOperationException();
    }
}

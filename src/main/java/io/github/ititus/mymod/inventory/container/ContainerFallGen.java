package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.tile.TileFallGen;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerFallGen extends ContainerBase<TileFallGen> {

    public ContainerFallGen(EntityPlayer player, TileFallGen tile) {
        super(player, tile);
        addPlayerInventory(8, 84);
    }

}

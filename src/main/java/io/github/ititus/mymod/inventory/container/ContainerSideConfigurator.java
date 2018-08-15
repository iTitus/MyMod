package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSideConfigurator extends ContainerBase<TileBase> {

    public ContainerSideConfigurator(EntityPlayer player, TileBase tile) {
        super(player, tile);
    }
}

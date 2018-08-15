package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetEnergy;
import io.github.ititus.mymod.inventory.container.ContainerFallGen;
import io.github.ititus.mymod.tile.TileFallGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFallGen extends GuiBase<TileFallGen, ContainerFallGen> {

    public GuiFallGen(ContainerFallGen container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        addWidget(new WidgetEnergy(79, 16, getTile().energy));
    }
}

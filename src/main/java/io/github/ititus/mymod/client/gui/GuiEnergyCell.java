package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetArrow;
import io.github.ititus.mymod.client.gui.widget.WidgetEnergy;
import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerEnergyCell;
import io.github.ititus.mymod.tile.TileEnergyCell;
import io.github.ititus.mymod.util.MathUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergyCell extends GuiBase<TileEnergyCell, ContainerEnergyCell> {

    public GuiEnergyCell(ContainerEnergyCell container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        addWidget(new WidgetEnergy(79, 16, getTile().getEnergy()));
        addWidget(new WidgetSlot(31, 28));
        addWidget(new WidgetSlot(31, 52));
        addWidget(new WidgetSlot(127, 28));
        addWidget(new WidgetSlot(127, 52));
        addWidget(new WidgetArrow(53, 41, false, widget -> MathUtil.scaledClamp(getTile().getEnergyToDischarge(), getTile().getCapacityToDischarge(), WidgetArrow.WIDTH)));
        addWidget(new WidgetArrow(101, 41, false, widget -> MathUtil.scaledClamp(getTile().getEnergyToCharge(), getTile().getCapacityToCharge(), WidgetArrow.WIDTH)));
    }
}

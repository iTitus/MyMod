package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetArrow;
import io.github.ititus.mymod.client.gui.widget.WidgetFluidTank;
import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerTank;
import io.github.ititus.mymod.tile.TileTank;
import io.github.ititus.mymod.util.MathUtil;

public class GuiTank extends GuiBase<TileTank, ContainerTank> {

    public GuiTank(ContainerTank container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        addWidget(new WidgetFluidTank(79, 16, getTile().tank));
        addWidget(new WidgetSlot(31, 40));
        addWidget(new WidgetSlot(127, 40));
        addWidget(new WidgetArrow(53, 41, false, widget -> MathUtil.scaledClamp(getTile().getFluidToDrain(), getTile().getCapacityToDrain(), WidgetArrow.WIDTH)));
        addWidget(new WidgetArrow(101, 41, false, widget -> MathUtil.scaledClamp(getTile().getFluidToFill(), getTile().getCapacityToFill(), WidgetArrow.WIDTH)));
    }
}

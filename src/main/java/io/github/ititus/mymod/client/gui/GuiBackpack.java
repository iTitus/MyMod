package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerBackpack;
import io.github.ititus.mymod.tile.TileBase;

public class GuiBackpack extends GuiBase<TileBase, ContainerBackpack> {

    public GuiBackpack(ContainerBackpack container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        int slots = container.getBackpack().getItemHandler().getSlots();
        int slotsXMax = 9;
        for (int i = 0; i < slots; i++) {
            int x = i % slotsXMax;
            int y = i / slotsXMax;
            addWidget(new WidgetSlot(7 + 18 * x, 16 + 18 * y));
        }
    }

    @Override
    public String getGuiTitle() {
        return container.getStack().getDisplayName();
    }
}

package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerMultiChest;
import io.github.ititus.mymod.tile.TileMultiChest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@SideOnly(Side.CLIENT)
public class GuiMultiChest extends GuiBase<TileMultiChest, ContainerMultiChest> {

    public GuiMultiChest(ContainerMultiChest container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        TileMultiChest tile = getTile();
        if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (handler != null) {
                int slots = handler.getSlots();
                int slotsXMax = 9;
                for (int i = 0; i < slots; i++) {
                    int x = i % slotsXMax;
                    int y = i / slotsXMax;
                    addWidget(new WidgetSlot(7 + 18 * x, 16 + 18 * y));
                }
            }
        }
    }
}

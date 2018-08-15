package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetButtonText;
import io.github.ititus.mymod.client.gui.widget.WidgetCheckbox;
import io.github.ititus.mymod.client.gui.widget.WidgetLabel;
import io.github.ititus.mymod.client.gui.widget.WidgetTripleCheckbox;
import io.github.ititus.mymod.inventory.container.ContainerSideConfigurator;
import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.side.SideConfiguration;
import net.minecraft.util.EnumFacing;

public class GuiSideConfigurator extends GuiBase<TileBase, ContainerSideConfigurator> {

    private EnumFacing selectedFacing;

    public GuiSideConfigurator(ContainerSideConfigurator container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();

        addWidget(new WidgetCheckbox(7, 139, widget -> getTile().getSideConfiguration().isAutoImport(), (widget, enabled) -> {
            getTile().getSideConfiguration().setAutoImport(enabled);
            sendTileToServer();
        }));
        addWidget(new WidgetLabel(18, 140, "text.mymod.side.auto_import", false));

        addWidget(new WidgetCheckbox(7, 150, widget -> getTile().getSideConfiguration().isAutoExport(), (widget, enabled) -> {
            getTile().getSideConfiguration().setAutoExport(enabled);
            sendTileToServer();
        }));
        addWidget(new WidgetLabel(18, 151, "text.mymod.side.auto_export", false));

        for (int i = -1; i < EnumFacing.VALUES.length; i++) {
            EnumFacing facing = i < 0 ? null : EnumFacing.byIndex(i);
            addWidget(new WidgetButtonText(7 + (i < 0 ? 0 : i % 2) * 20, 32 + (i < 0 ? 0 : (i - (i % 2)) / 2 + 1) * 20, 20, 20, "text.mymod.facing." + (facing != null ? facing.getName() : "all") + ".short", true, widget -> selectedFacing != facing, widget -> {
                selectedFacing = facing;
                initGui();
            }));
        }

        addWidget(new WidgetButtonText(89, 139, 80, 20, "text.mymod.reset", true, widget -> {
            getTile().getSideConfiguration().reset();
            sendTileToServer();
        }));

        String facingString = "text.mymod.facing." + (selectedFacing != null ? selectedFacing.getName() : "all");
        addWidget(new WidgetLabel(xSize / 2, 6 + fontRenderer.FONT_HEIGHT, facingString, true));

        SideConfiguration cfg = getTile().getSideConfiguration();
        int x = 110, y = 32;

        int[] inputSlots = getTile().getInputSlots();
        for (int i = 0; i < inputSlots.length; i++) {
            int slot = inputSlots[i];
            addWidget(new WidgetTripleCheckbox(x - 1 - WidgetTripleCheckbox.WIDTH, y + i * 11, widget -> cfg.getInputSlotState(selectedFacing, slot), selectedFacing != null, (widget, state) -> {
                cfg.setInputSlotState(selectedFacing, slot, state);
                sendTileToServer();
            }));
            String text = "" + slot;
            addWidget(new WidgetLabel(x - 3 - WidgetTripleCheckbox.WIDTH - fontRenderer.getStringWidth(text), y + 1 + i * 11, text, false, false));
        }

        int[] outputSlots = getTile().getOutputSlots();
        for (int i = 0; i < outputSlots.length; i++) {
            int slot = outputSlots[i];
            addWidget(new WidgetTripleCheckbox(x + 1, y + i * 11, widget -> cfg.getOutputSlotState(selectedFacing, slot), selectedFacing != null, (widget, state) -> {
                cfg.setOutputSlotState(selectedFacing, slot, state);
                sendTileToServer();
            }));
            String text = "" + slot;
            addWidget(new WidgetLabel(x + 3 + WidgetTripleCheckbox.WIDTH, y + 1 + i * 11, text, false, false));
        }
    }
}

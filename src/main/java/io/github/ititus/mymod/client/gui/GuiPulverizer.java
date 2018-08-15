package io.github.ititus.mymod.client.gui;

import io.github.ititus.mymod.client.gui.widget.WidgetArrow;
import io.github.ititus.mymod.client.gui.widget.WidgetEnergy;
import io.github.ititus.mymod.client.gui.widget.WidgetSlot;
import io.github.ititus.mymod.inventory.container.ContainerPulverizer;
import io.github.ititus.mymod.tile.TilePulverizer;
import io.github.ititus.mymod.util.MathUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPulverizer extends GuiBase<TilePulverizer, ContainerPulverizer> {

    public GuiPulverizer(ContainerPulverizer container) {
        super(container, 176, 166, TEXTURE_BLANK);
    }

    @Override
    public void initGui() {
        super.initGui();
        addPlayerInventory(8, 84);
        addWidget(new WidgetSlot(29, 34));
        addWidget(new WidgetArrow(56, 35, false, widget -> MathUtil.scaledClamp(getTile().progress, getTile().maxProgress, WidgetArrow.WIDTH)));
        addWidget(new WidgetSlot(87, 34));
        addWidget(new WidgetSlot(107, 34));
        addWidget(new WidgetEnergy(151, 10, getTile().energy));
    }
}

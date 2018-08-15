package io.github.ititus.mymod.client.gui.widget;

import io.github.ititus.mymod.util.MathUtil;
import io.github.ititus.mymod.util.energy.EnergyStorageBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class WidgetEnergy extends WidgetBase {

    private static final int WIDTH = 18, HEIGHT = 66;
    private static final int U = 0, V = 0;

    private final EnergyStorageBase energy;

    public WidgetEnergy(int x, int y, EnergyStorageBase energy) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
        this.energy = energy;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(partialTicks, mouseX, mouseY);
        int hFixedSize = getHFixed(bounds.getHeight() - 2);
        int hFixedTex = getHFixed(HEIGHT - 2);
        drawTexturedModalRect(gui.getGuiLeft() + bounds.getX() + 1, gui.getGuiTop() + bounds.getY() + bounds.getHeight() - 1 - hFixedSize, bounds.getWidth() - 2, hFixedSize, WIDTH, HEIGHT - 2 - hFixedTex, WIDTH - 2, hFixedTex, SHEET_WIDTH, SHEET_HEIGHT);
    }

    @Override
    public void addInformation(List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.translateToLocal("text.mymod.energy"));
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("text.mymod.energy.stored", energy.getEnergyStored(), energy.getMaxEnergyStored()));
    }

    private int getHFixed(int max) {
        return MathUtil.scaledClamp(energy.getEnergyStored(), energy.getMaxEnergyStored(), max);
    }
}

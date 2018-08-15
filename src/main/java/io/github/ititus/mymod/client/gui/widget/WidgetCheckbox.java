package io.github.ititus.mymod.client.gui.widget;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class WidgetCheckbox extends WidgetBase {

    public static final int WIDTH = 9, HEIGHT = 9;
    private static final int U = 34, V = 32;
    private final Predicate<WidgetCheckbox> isEnabled;
    private final BiConsumer<WidgetCheckbox, Boolean> onToggle;

    public WidgetCheckbox(int x, int y, Predicate<WidgetCheckbox> isEnabled, BiConsumer<WidgetCheckbox, Boolean> onToggle) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
        this.isEnabled = isEnabled;
        this.onToggle = onToggle;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(partialTicks, mouseX, mouseY);
        if (isEnabled.test(this)) {
            drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), bounds.getWidth(), bounds.getHeight(), u + texWidth, v, texWidth, texHeight, SHEET_WIDTH, SHEET_HEIGHT);
        }
    }

    @Override
    public void onClick(int mouseButton) {
        if (mouseButton == 0) {
            gui.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1));
            onToggle.accept(this, !isEnabled.test(this));
        }
    }

}

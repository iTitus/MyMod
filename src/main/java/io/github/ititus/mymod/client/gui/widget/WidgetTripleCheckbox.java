package io.github.ititus.mymod.client.gui.widget;

import io.github.ititus.mymod.util.TripleBool;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class WidgetTripleCheckbox extends WidgetBase {

    public static final int WIDTH = 9, HEIGHT = 9;
    private static final int U = 34, V = 32;
    private final Function<WidgetTripleCheckbox, TripleBool> state;
    private final boolean enableDefault;
    private final BiConsumer<WidgetTripleCheckbox, TripleBool> onToggle;

    public WidgetTripleCheckbox(int x, int y, Function<WidgetTripleCheckbox, TripleBool> state, boolean enableDefault, BiConsumer<WidgetTripleCheckbox, TripleBool> onToggle) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
        this.state = state;
        this.enableDefault = enableDefault;
        this.onToggle = onToggle;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(partialTicks, mouseX, mouseY);
        TripleBool tripleBool = state.apply(this);
        int offsetX = 0, offsetY = 0;
        switch (tripleBool) {
            case DEFAULT:
                offsetX = texWidth;
                offsetY = texHeight;
                break;
            case TRUE:
                offsetX = texWidth;
                break;
            case FALSE:
                offsetY = texWidth;
                break;

        }
        drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), bounds.getWidth(), bounds.getHeight(), u + offsetX, v + offsetY, texWidth, texHeight, SHEET_WIDTH, SHEET_HEIGHT);
    }

    @Override
    public void onClick(int mouseButton) {
        if (mouseButton == 0) {
            gui.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1));
            onToggle.accept(this, enableDefault ? state.apply(this).next() : TripleBool.get(!state.apply(this).get()));
        } else if (enableDefault && mouseButton == 1) {
            gui.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1));
            onToggle.accept(this, state.apply(this).prev());
        }
    }

}

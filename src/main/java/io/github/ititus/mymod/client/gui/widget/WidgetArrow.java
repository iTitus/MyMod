package io.github.ititus.mymod.client.gui.widget;

import java.util.function.ToIntFunction;

public class WidgetArrow extends WidgetBase {

    public static final int WIDTH = 22, HEIGHT = 16;
    private static final int U = 34, V = 0;

    private boolean reverse;
    private ToIntFunction<WidgetArrow> offsetSupplier;

    public WidgetArrow(int x, int y, boolean reverse, ToIntFunction<WidgetArrow> offsetSupplier) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
        this.reverse = reverse;
        this.offsetSupplier = offsetSupplier;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        if (reverse) {
            // TODO: FIXME
            throw new UnsupportedOperationException();
        } else {
            super.drawBackground(partialTicks, mouseX, mouseY);
        }
        int offset = Math.max(0, Math.min(WIDTH, offsetSupplier.applyAsInt(this)));
        if (offset > 0) {
            if (reverse) {
                // TODO: Implement
            } else {
                drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), offset, bounds.getHeight(), u, v + texHeight, offset, texHeight, SHEET_WIDTH, SHEET_HEIGHT);
            }
        }
    }
}

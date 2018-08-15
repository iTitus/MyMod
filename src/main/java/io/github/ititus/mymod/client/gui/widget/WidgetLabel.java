package io.github.ititus.mymod.client.gui.widget;

import com.google.common.base.Strings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.util.Rectangle;

import java.util.function.Function;

public class WidgetLabel extends WidgetBase {

    private static final int COLOR = 0x404040;

    private final Function<WidgetLabel, String> label;
    private final boolean centered;

    public WidgetLabel(int x, int y, String label, boolean centered) {
        this(x, y, label, true, centered);
    }

    public WidgetLabel(int x, int y, String label, boolean translate, boolean centered) {
        this(x, y, widget -> translate ? I18n.translateToLocal(label) : label, centered);
    }

    public WidgetLabel(int x, int y, Function<WidgetLabel, String> label, boolean centered) {
        super(x, y, 0, 0);
        this.label = label;
        this.centered = centered;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        String string = label.apply(this);
        if (!Strings.isNullOrEmpty(string)) {
            FontRenderer fr = gui.getFontRenderer();
            fr.drawString(string, bounds.getX() - (centered ? fr.getStringWidth(string) / 2 : 0), bounds.getY(), COLOR);
        }
    }

    @Override
    public Rectangle getBounds() {
        /*
        String string = label.apply(this);
        if (!Strings.isNullOrEmpty(string)) {
            bounds.setSize(gui.getFontRenderer().getStringWidth(string), gui.getFontRenderer().FONT_HEIGHT);
        }
        */
        return super.getBounds();
    }
}

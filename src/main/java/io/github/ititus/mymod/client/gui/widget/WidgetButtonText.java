package io.github.ititus.mymod.client.gui.widget;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class WidgetButtonText extends WidgetBase {

    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    private static final String ELLIPSIS = "...";
    private static final int COLOR = 0xE0E0E0, COLOR_DISABLED = 0xA0A0A0, COLOR_HOVERED = 0xFFFFA0;
    private static final int U = 0, V = 46;
    private static final int TEX_WIDTH = 200, TEX_HEIGHT = 20;
    private static final int BORDER_TOP = 2, BORDER_BOTTOM = 3, BORDER_LEFT = 2, BORDER_RIGHT = 2;
    private final Function<WidgetButtonText, String> label;
    private final Predicate<WidgetButtonText> isEnabled;
    private final Consumer<WidgetButtonText> onClick;

    public WidgetButtonText(int x, int y, int w, int h, String label, boolean translate, Consumer<WidgetButtonText> onClick) {
        this(x, y, w, h, label, translate, widget -> true, onClick);
    }

    public WidgetButtonText(int x, int y, int w, int h, String label, boolean translate, Predicate<WidgetButtonText> isEnabled, Consumer<WidgetButtonText> onClick) {
        this(x, y, w, h, widget -> translate ? I18n.translateToLocal(label) : label, isEnabled, onClick);
    }

    public WidgetButtonText(int x, int y, int w, int h, Function<WidgetButtonText, String> label, Predicate<WidgetButtonText> isEnabled, Consumer<WidgetButtonText> onClick) {
        super(x, y, w, h, U, V, TEX_WIDTH, TEX_HEIGHT);
        this.label = label;
        this.isEnabled = isEnabled;
        this.onClick = onClick;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), u, v + (!isEnabled.test(this) ? 0 : bounds.contains(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop()) ? 2 : 1) * 20, bounds.getWidth(), bounds.getHeight(), texWidth, texHeight, BORDER_TOP, BORDER_BOTTOM, BORDER_LEFT, BORDER_RIGHT, zLevel);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        FontRenderer fr = gui.getFontRenderer();
        String label = this.label.apply(this);
        int labelWidth = fr.getStringWidth(label);
        int ellipsisWidth = fr.getStringWidth(ELLIPSIS);
        if (labelWidth > bounds.getWidth() - 6 && labelWidth > ellipsisWidth) {
            label = fr.trimStringToWidth(label, bounds.getWidth() - 6 - ellipsisWidth).trim() + ELLIPSIS;
        }
        drawCenteredString(fr, label, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + (bounds.getHeight() - fr.FONT_HEIGHT + 1) / 2, !isEnabled.test(this) ? COLOR_DISABLED : bounds.contains(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop()) ? COLOR_HOVERED : COLOR);
    }

    @Override
    public void onClick(int mouseButton) {
        if (isEnabled.test(this) && mouseButton == 0) {
            gui.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1));
            onClick.accept(this);
        }
    }

}

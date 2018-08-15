package io.github.ititus.mymod.client.gui.widget;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.client.gui.GuiBase;
import io.github.ititus.mymod.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID)
public class WidgetSlot extends WidgetBase {

    public static final int WIDTH = 18, HEIGHT = 18;
    private static final int U = 56, V = 0;

    public WidgetSlot(int x, int y) {
        super(x, y, WIDTH, HEIGHT, U, V, WIDTH, HEIGHT);
    }

    protected WidgetSlot(int x, int y, int w, int h, int u, int v, int texWidth, int texHeight) {
        super(x, y, w, h, u, v, texWidth, texHeight);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() || GuiScreen.isCtrlKeyDown()) {
            ItemStack stack = event.getItemStack();
            if (!stack.isEmpty()) {
                GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
                if (currentScreen instanceof GuiBase<?, ?>) {
                    GuiBase<?, ?> gui = (GuiBase<?, ?>) currentScreen;
                    TileBase tile = gui.getTile();
                    if (tile != null && tile.hasSideConfiguration()) {
                        Slot slot = gui.getSlotUnderMouse();
                        if (slot != null && !(slot.inventory instanceof InventoryPlayer) && slot.getHasStack()) {
                            event.getToolTip().add(I18n.translateToLocalFormatted("text.mymod:slot.index", slot.getSlotIndex()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(List<String> tooltip, boolean advanced) {
        if (advanced || GuiScreen.isCtrlKeyDown()) {
            TileBase tile = gui.getTile();
            if (tile != null && tile.hasSideConfiguration()) {
                Slot slot = gui.getSlotUnderMouse();
                if (slot != null && !(slot.inventory instanceof InventoryPlayer) && !slot.getHasStack()) {
                    tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("text.mymod:slot.index", slot.getSlotIndex()));
                }
            }
        }
    }
}

package io.github.iTitus.MyMod.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.iTitus.MyMod.common.handler.ConfigHandler;
import io.github.iTitus.MyMod.common.util.MathUtil;
import io.github.iTitus.MyMod.common.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderUtil {

    public static final FontRenderer FR = Minecraft.getMinecraft().fontRenderer;
    private static final double PI = Math.PI;

    public static void drawClock(double x, double y, double radius) {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        setColor(ConfigHandler.color);
        drawHollowCircleShort(x, y, radius);
        drawLines(x, y, radius);
        drawHours(x, y, radius);
        drawMinutes(x, y, radius);
        if (ConfigHandler.seconds)
            drawSeconds(x, y, radius);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        drawNumbers(x, y, radius);

    }

    public static void drawHollowCircle(double x, double y, double radius) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawHollowCircleShort(x, y, radius);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

    }

    public static int[] getColor(int color) {

        String hexColor = StringUtil.makeNDigits(Integer.toHexString(color), 6,
                "0");

        return new int[]{Integer.valueOf(hexColor.substring(0, 2), 16),
                Integer.valueOf(hexColor.substring(2, 4), 16),
                Integer.valueOf(hexColor.substring(4, 6), 16)};
    }

    public static int getColor(int r, int g, int b) {
        return Integer
                .valueOf(
                        StringUtil.makeNDigits(Integer.toHexString(r), 2, "0")
                                + StringUtil.makeNDigits(
                                Integer.toHexString(g), 2, "0")
                                + StringUtil.makeNDigits(
                                Integer.toHexString(b), 2, "0"), 16);
    }

    public static void setColor(int color) {

        int[] colors = getColor(color);

        GL11.glColor3d(colors[0] / 255D, colors[1] / 255D, colors[2] / 255D);

    }

    private static void drawHollowCircleShort(double x, double y, double radius) {
        GL11.glLineWidth(1);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int angle = 0; angle <= 360; angle++) {
            GL11.glVertex2d(
                    x + (radius * MathHelper.sin(MathUtil.degToRad(angle))),
                    y + (radius * MathHelper.cos(MathUtil.degToRad(angle))));
        }
        GL11.glEnd();
    }

    private static void drawHours(double x, double y, double radius) {
        GL11.glLineWidth(3);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(
                x
                        + ((radius - (radius / 2D)) * MathHelper.cos(MathUtil.degToRad(((TimeUtil
                        .getHour() + ((TimeUtil.getMin() + (TimeUtil
                        .getSec() / 60F)) / 60F)) / 12F) * 360F)
                        - (MathUtil.PI / 2))),
                y
                        + ((radius - (radius / 2D)) * MathHelper.sin(MathUtil.degToRad(((TimeUtil
                        .getHour() + ((TimeUtil.getMin() + (TimeUtil
                        .getSec() / 60F)) / 60F)) / 12F) * 360F)
                        - (MathUtil.PI / 2))));
        GL11.glEnd();
    }

    private static void drawLines(double x, double y, double radius) {
        GL11.glLineWidth(1);
        for (int angle = 6; angle <= 360; angle += 6) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2d(
                    x
                            + ((radius - (radius / ((angle % 5 == 0) ? 8D : 16D))) * MathHelper
                            .sin(MathUtil.degToRad(angle))),
                    y
                            + ((radius - (radius / ((angle % 5 == 0) ? 8D : 16D))) * MathHelper
                            .cos(MathUtil.degToRad(angle))));

            GL11.glVertex2d(
                    x + (radius * MathHelper.sin(MathUtil.degToRad(angle))),
                    y + (radius * MathHelper.cos(MathUtil.degToRad(angle))));

            GL11.glEnd();

        }

    }

    private static void drawMinutes(double x, double y, double radius) {
        GL11.glLineWidth(2);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(
                x
                        + ((radius - (radius / 5D)) * MathHelper.cos(MathUtil
                        .degToRad(((TimeUtil.getMin() + (TimeUtil
                                .getSec() / 60F)) / 60F) * 360F)
                        - (MathUtil.PI / 2))),
                y
                        + ((radius - (radius / 5D)) * MathHelper.sin(MathUtil
                        .degToRad(((TimeUtil.getMin() + (TimeUtil
                                .getSec() / 60F)) / 60F) * 360F)
                        - (MathUtil.PI / 2))));
        GL11.glEnd();
    }

    private static void drawNumbers(double x, double y, double radius) {

        String hour = ""
                + TimeUtil.convertToAMPMIfNecessary(TimeUtil.isPM() ? 24 : 12);
        FR.drawString(hour, (int) (x - (FR.getStringWidth(hour) / 2) + 1),
                (int) (y - radius - FR.FONT_HEIGHT), ConfigHandler.color);

        hour = "" + TimeUtil.convertToAMPMIfNecessary(TimeUtil.isPM() ? 15 : 3);
        FR.drawString(hour, (int) (x + radius + 1),
                (int) (y - (FR.FONT_HEIGHT / 2)), ConfigHandler.color);

        hour = "" + TimeUtil.convertToAMPMIfNecessary(TimeUtil.isPM() ? 18 : 6);
        FR.drawString(hour, (int) (x - (FR.getStringWidth(hour) / 2)), (int) (y
                + radius + 1), ConfigHandler.color);

        hour = "" + TimeUtil.convertToAMPMIfNecessary(TimeUtil.isPM() ? 21 : 9);
        FR.drawString(hour, (int) (x - radius - FR.getStringWidth(hour)),
                (int) (y - (FR.FONT_HEIGHT / 2)), ConfigHandler.color);

    }

    private static void drawSeconds(double x, double y, double radius) {
        GL11.glLineWidth(1);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(
                x
                        + ((radius - (radius / 10D) - 1) * MathHelper.cos(MathUtil
                        .degToRad((TimeUtil.getSec() / 60F) * 360F)
                        - (MathUtil.PI / 2))),
                y
                        + ((radius - (radius / 10D) - 1) * MathHelper.sin(MathUtil
                        .degToRad((TimeUtil.getSec() / 60F) * 360F)
                        - (MathUtil.PI / 2))));
        GL11.glEnd();
    }

}

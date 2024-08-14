package com.yuanno.blockclover.api.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

import java.awt.*;

public class RendererHelper {

    public static void drawIcon(ResourceLocation rs, MatrixStack matrixStack, int x, int y, int z, int u, int v)
    {
        drawIcon(rs, matrixStack, x, y, z, u, v, 1, 1, 1, 1);
    }

    public static void drawIcon(ResourceLocation rs, MatrixStack matrixStack, int x, int y, int z, int u, int v, int intColor)
    {
        Color color = new Color(intColor);
        drawIcon(rs, matrixStack, x, y, z, u, v, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1);
    }

    /**
     * As the name says, made to draw any icon on the screen
     * @author Wynd-sensei
     */
    public static void drawIcon(ResourceLocation rs, MatrixStack matrixStack, int x, int y, int z, int u, int v, float red, float green, float blue, float alpha)
    {
        Matrix4f matrix = matrixStack.last().pose();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        Minecraft.getInstance().getTextureManager().bind(rs);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix, x, y + v, z).color(red, green, blue, alpha).uv(0.0f, 1.0f).endVertex();
        bufferbuilder.vertex(matrix, x + u, y + v, z).color(red, green, blue, alpha).uv(1.0f, 1.0f).endVertex();
        bufferbuilder.vertex(matrix, x + u, y, z).color(red, green, blue, alpha).uv(1.0f, 0.0f).endVertex();
        bufferbuilder.vertex(matrix, x, y, z).color(red, green, blue, alpha).uv(0f, 0f).endVertex();
        Tessellator.getInstance().end();
        RenderSystem.disableBlend();
    }
}

package com.nakuring.enhanced_boss_bars.implementation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBarGraphics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class FabricBossBarGraphics implements BossBarGraphics {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer textRenderer;
    private DrawContext context;

    public FabricBossBarGraphics() {
        this.textRenderer = client.textRenderer;
    }

    @Override
    public int screenWidth() {
        return client.getWindow().getScaledWidth();
    }

    @Override
    public int screenHeight() {
        return client.getWindow().getScaledHeight();
    }

    @Override
    public int textWidth(String text) {
        return textRenderer.getWidth(text);
    }

    @Override
    public void drawTexture(
            Identifier texture,
            int x, int y,
            int u, int v,
            int width, int height,
            int textureWidth, int textureHeight
    ) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        context.drawTexture(
                texture,
                x, y,
                u, v,
                width, height,
                textureWidth, textureHeight
        );

        RenderSystem.disableBlend();
    }

    @Override
    public void drawText(
            String text,
            int x,
            int y,
            int color,
            boolean shadow
    ) {
        context.drawText(textRenderer, text, x, y, color, shadow);
    }

    public void begin(DrawContext context) {
        this.context = context;
    }

    @Override
    public void pushScale(float scale, float centerX, float centerY) {
        var matrices = context.getMatrices();
        matrices.push();
        matrices.translate(centerX, centerY, 0.0);
        matrices.scale(scale, scale, 1.0f);
        matrices.translate(-centerX, -centerY, 0.0);
    }

    @Override
    public void popScale() {
        context.getMatrices().pop();
    }

    @Override
    public float effectiveScale(int scale) {
        return scale / (float) client.getWindow().getScaleFactor();
    }
}
package com.nakuring.enhanced_boss_bars.mixin;

import com.nakuring.enhanced_boss_bars.config.ClothConfigScreen;
import com.nakuring.enhanced_boss_bars.config.render.BossBarPreviewRenderer;
import com.nakuring.enhanced_boss_bars.implementation.FabricBossBarGraphics;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(me.shedaniel.clothconfig2.gui.ClothConfigScreen.class)
public abstract class ClothConfigScreenMixin {

    @Unique
    private final FabricBossBarGraphics graphics = new FabricBossBarGraphics();

    @Inject(method = "render", at = @At("TAIL"))
    private void renderPreview(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta,
            CallbackInfo ci
    ) {

        if ((Object) this != ClothConfigScreen.getCurrentScreen()) {
            return;
        }

        graphics.begin(context);

        BossBarPreviewRenderer.render(graphics);
    }

}
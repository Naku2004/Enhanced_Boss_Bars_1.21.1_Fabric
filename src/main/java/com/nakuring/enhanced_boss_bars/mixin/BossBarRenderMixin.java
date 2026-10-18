package com.nakuring.enhanced_boss_bars.mixin;

import com.nakuring.enhanced_boss_bars.EnhancedBossBars;
import com.nakuring.enhanced_boss_bars.config.ConfigManager;
import com.nakuring.enhanced_boss_bars.implementation.FabricBossBarGraphics;
import com.nakuring.enhanced_boss_bars.implementation.FabricBossBars;
import com.nakuring.enhanced_boss_bars.render.BossBarRenderPipeline;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(BossBarHud.class)
public abstract class BossBarRenderMixin {

    @Shadow
    private Map<UUID, ClientBossBar> bossBars;

    @Unique
    private final BossBarRenderPipeline pipeline = new BossBarRenderPipeline();

    @Unique
    private final FabricBossBarGraphics graphics = new FabricBossBarGraphics();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(
            DrawContext context,
            CallbackInfo ci
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        BossBars bars = new FabricBossBars(bossBars);
        if (client.player == null || !ConfigManager.generalConfig().enhanced_boss_bars_enabled) return;

        ci.cancel();

        //EnhancedBossBars.LOGGER.info("Lanzando pipeline...");

        graphics.begin(context);
        pipeline.run(graphics, bars);
    }
}
package com.nakuring.enhanced_boss_bars.mixin;

import com.nakuring.enhanced_boss_bars.EnhancedBossBars;
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
        if (client.player == null) return;

        ci.cancel();

        graphics.begin(context);

        pipeline.run(
                graphics,
                bars
        );

        EnhancedBossBars.LOGGER.info("Lanzando pipeline...");
    }
}

    /*
    private void run(DrawContext context, CallbackInfo ci) {
        ci.cancel();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (second_phase_active == null) second_phase_active = new HashMap<>();

        GeneralConfig general_config = ConfigManager.getGeneralConfig();
        int y = 0;
        int boss_bar_rendered = 0;
        int max_width = 0;
        boolean is_generic;

        for (Map.Entry<UUID, ClientBossBar> entry : bossBars.entrySet()) {

            ClientBossBar client_boss_bar = entry.getValue();
            UUID uuid = entry.getKey();

            if (boss_bar_rendered >= general_config.max_boss_bars) break;


            BossBarKey key = getBossBarKey(client_boss_bar);
            BossBarEntry boss_bar_entry = BossBarsManager.getBossBar(key);
            BossBarData boss_bar;

            int screen_height = MinecraftClient.getInstance().getWindow().getScaledHeight();
            double percent = general_config.position_y / 100.0;
            int boss_bar_height;

            if (boss_bar_entry != null && boss_bar_entry.data() != null && ConfigManager.barConfig(boss_bar_entry.key()).enabled) {
                boss_bar = boss_bar_entry.data();
                boss_bar_height = boss_bar.overlay_height() + boss_bar.overlay_offset_y();
                is_generic = false;
            }
            else {
                BossBarEntry generic_boss_bar_entry = BossBarsManager.getBossBar(
                        new BossBarKey(client_boss_bar.getColor().getName(), BossBarKey.Type.color)
                );
                assert generic_boss_bar_entry != null;
                boss_bar = generic_boss_bar_entry.data();
                boss_bar_height = boss_bar.overlay_height() + boss_bar.overlay_offset_y() + 5;
                is_generic = true;
            }

            if (y == 0) y = (int) ((screen_height - boss_bar_height) * percent);

            y = prepareBossBar(context, boss_bar, client_boss_bar, uuid, y, is_generic, general_config);


            boss_bar_rendered++;
        }

        second_phase_active.keySet().removeIf(uuid -> !bossBars.containsKey(uuid));
    }


    @Unique
    private int prepareBossBar(DrawContext context, BossBarData boss_bar, ClientBossBar client_boss_bar, UUID uuid, int y, boolean isGeneric, GeneralConfig general_config) {
        int screen_width = MinecraftClient.getInstance().getWindow().getScaledWidth();

        if (isGeneric) y+= 5;

        int x = getBossBarX(screen_width, boss_bar.overlay_width());

        renderHealth(context, x, y, boss_bar, client_boss_bar, uuid);

        renderOverlay(context, x, y, boss_bar, client_boss_bar, uuid);

        if (isGeneric) renderName(context, client_boss_bar, x, y - 4);

        y += boss_bar.overlay_height() + 1;

        return y;
    }

    @Unique
    private void renderHealth(DrawContext context, int x, int y, BossBarData boss_bar, ClientBossBar client_boss_bar, UUID uuid) {

        int health_x = x - boss_bar.health_width() / 2;

        // Second Phase (if has)
        if((boss_bar.hasSecondPhase() && (double) client_boss_bar.getPercent() < boss_bar.threshold_phase()) || second_phase_active.getOrDefault(uuid, false)){
            second_phase_active.putIfAbsent(uuid, Boolean.TRUE);
            context.drawTexture(
                    boss_bar.second_phase_background(),
                    health_x, y + boss_bar.health_y(), 0, 0,
                    boss_bar.health_width(),
                    boss_bar.health_height(),
                    boss_bar.health_width(),
                    boss_bar.health_height()
            );

            context.drawTexture(
                    boss_bar.second_phase_color(),
                    health_x, y + boss_bar.health_y(), 0, 0,
                    (int) (boss_bar.health_width() * client_boss_bar.getPercent()),
                    boss_bar.health_height(),
                    boss_bar.health_width(),
                    boss_bar.health_height()
            );
            return;
        }

        context.drawTexture(
                boss_bar.health_background(),
                health_x, y + boss_bar.health_y(), 0, 0,
                boss_bar.health_width(),
                boss_bar.health_height(),
                boss_bar.health_width(),
                boss_bar.health_height()
        );

        context.drawTexture(
                boss_bar.health_color(),
                health_x, y + boss_bar.health_y(), 0, 0,
                (int) (boss_bar.health_width() * client_boss_bar.getPercent()),
                boss_bar.health_height(),
                boss_bar.health_width(),
                boss_bar.health_height()
        );
    }

    @Unique
    private void renderOverlay(DrawContext context, int x, int y, BossBarData boss_bar, ClientBossBar client_boss_bar, UUID uuid) {

        int overlay_x = x - boss_bar.overlay_width() / 2 + boss_bar.overlay_offset_x();

        // Second Phase (if has)
        if((boss_bar.hasSecondPhase() && (double) client_boss_bar.getPercent() < boss_bar.threshold_phase()) || second_phase_active.getOrDefault(uuid, false)){
            context.drawTexture(
                    boss_bar.second_phase_overlay(),
                    overlay_x + boss_bar.overlay_offset_x(), y, 0, 0,
                    boss_bar.overlay_width(),
                    boss_bar.overlay_height(),
                    boss_bar.overlay_width(),
                    boss_bar.overlay_height()
            );
            return;
        }

        context.drawTexture(
                boss_bar.overlay(),
                overlay_x + boss_bar.overlay_offset_x(), y, 0, 0,
                boss_bar.overlay_width(),
                boss_bar.overlay_height(),
                boss_bar.overlay_width(),
                boss_bar.overlay_height()
        );
    }

    @Unique
    private void renderName(DrawContext context, ClientBossBar client_boss_bar, int x, int y){

        Text boss_name = client_boss_bar.getName();//.copy().styled(style -> style.withFont(romulus_font));
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        int name_x = x - textRenderer.getWidth(boss_name) / 2;

        int[][] offsets = {
                {-1,0}, {1,0}, {0,-1}, {0,1},
                {-1,-1}, {-1,1}, {1,-1}, {1,1}
        };

        for (int[] o : offsets) {
            context.drawText(textRenderer, boss_name, name_x + o[0], y + o[1], 0x161616, false);
        }

        int color = colors.get(client_boss_bar.getColor().getName());

        context.drawText(textRenderer, boss_name, name_x, y, color, false);
    }

    private int getBossBarX(int screen_width, int bar_width) {
        GeneralConfig config = ConfigManager.getGeneralConfig();
        double percent = config.position_x / 100.0;

        return switch (config.horizontal_alignment) {

            case Left -> (int) (screen_width * percent + (double) bar_width / 2);

            case Center -> (int) ((screen_width - bar_width) * percent + (double) bar_width / 2);

            case Right -> (int) (screen_width * percent - (double) bar_width / 2);
        };
    }

    @Unique
    private BossBarKey getBossBarKey(ClientBossBar clientBossBar) {

        Text name = clientBossBar.getName();

        if (name.getContent() instanceof TranslatableTextContent translatable) {
            return new BossBarKey(
                    translatable.getKey(),
                    BossBarKey.Type.translation
            );
        }

        return new BossBarKey(
                name.getString(),
                BossBarKey.Type.contains
        );
    }

     */

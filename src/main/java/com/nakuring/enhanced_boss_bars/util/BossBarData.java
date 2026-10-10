package com.nakuring.enhanced_boss_bars.util;

import net.minecraft.util.Identifier;

public record BossBarData(
        String author,
        String mod_id,

        Identifier health_background,
        Identifier health_color,
        Identifier overlay,

        Identifier second_phase_background,
        Identifier second_phase_color,
        Identifier second_phase_overlay,

        int health_width,
        int health_height,
        int health_y,

        int overlay_width,
        int overlay_height,
        int overlay_offset_x,
        int overlay_offset_y,

        int health_texture_coord_x,
        int health_texture_coord_y,

        int overlay_texture_coord_x,
        int overlay_texture_coord_y,

        double threshold_phase
) {

    public boolean hasSecondPhase() {
        return threshold_phase > 0;
    }
}

package com.nakuring.enhanced_boss_bars.util;

import net.minecraft.util.Identifier;

public record BossBarData(
        String author,
        String mod_id,

        Identifier health_background_texture,
        Identifier health_color_texture,
        Identifier overlay_texture,

        int health_bar_width,
        int health_bar_height,
        int health_offset_x,
        int health_offset_y,

        int overlay_width,
        int overlay_height,
        int overlay_offset_x,
        int overlay_offset_y,

        int health_background_texture_width,
        int health_background_texture_height,
        int health_color_texture_width,
        int health_color_texture_height,
        int overlay_texture_width,
        int overlay_texture_height,

        int health_background_texture_coord_x,
        int health_background_texture_coord_y,
        int health_color_texture_coord_x,
        int health_color_texture_coord_y,
        int overlay_texture_coord_x,
        int overlay_texture_coord_y,

        Identifier second_phase_health_background_texture,
        Identifier second_phase_health_color_texture,
        Identifier second_phase_overlay_texture,

        int second_phase_overlay_width,
        int second_phase_overlay_height,
        int second_phase_overlay_offset_x,
        int second_phase_overlay_offset_y,

        int second_phase_health_background_texture_width,
        int second_phase_health_background_texture_height,
        int second_phase_health_color_texture_width,
        int second_phase_health_color_texture_height,
        int second_phase_overlay_texture_width,
        int second_phase_overlay_texture_height,

        int second_phase_health_background_texture_coord_x,
        int second_phase_health_background_texture_coord_y,
        int second_phase_health_color_texture_coord_x,
        int second_phase_health_color_texture_coord_y,
        int second_phase_overlay_texture_coord_x,
        int second_phase_overlay_texture_coord_y,

        double threshold_phase,

        boolean health_bar_before_overlay
) {

    public boolean hasSecondPhase() {
        return threshold_phase > 0;
    }
}

package com.nakuring.enhanced_boss_bars.bars_loader;

import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.util.Identifier;

public class BossBarDefinition {

    public String author;
    public String key;
    public BossBarKey.Type type;
    public String mod_id;

    public Identifier health_background_texture;
    public Identifier health_color_texture;
    public Identifier overlay_texture;

    public int health_bar_width;
    public int health_bar_height;
    public int health_offset_x = 0;
    public int health_offset_y;

    public int overlay_width;
    public int overlay_height;
    public int overlay_offset_x = 0;
    public int overlay_offset_y = 0;

    public Integer health_background_texture_width;
    public Integer health_background_texture_height;
    public Integer health_color_texture_width;
    public Integer health_color_texture_height;
    public Integer overlay_texture_width;
    public Integer overlay_texture_height;

    public int health_background_texture_coord_x = 0;
    public int health_background_texture_coord_y = 0;
    public int health_color_texture_coord_x = 0;
    public int health_color_texture_coord_y = 0;
    public int overlay_texture_coord_x = 0;
    public int overlay_texture_coord_y = 0;

    public Identifier second_phase_health_background_texture = null;
    public Identifier second_phase_health_color_texture = null;
    public Identifier second_phase_overlay_texture = null;

    public Integer second_phase_overlay_width;
    public Integer second_phase_overlay_height;
    public Integer second_phase_overlay_offset_x;
    public Integer second_phase_overlay_offset_y;

    public Integer second_phase_health_background_texture_width;
    public Integer second_phase_health_background_texture_height;
    public Integer second_phase_health_color_texture_width;
    public Integer second_phase_health_color_texture_height;
    public Integer second_phase_overlay_texture_width;
    public Integer second_phase_overlay_texture_height;

    public int second_phase_health_background_texture_coord_x = 0;
    public int second_phase_health_background_texture_coord_y = 0;
    public int second_phase_health_color_texture_coord_x = 0;
    public int second_phase_health_color_texture_coord_y = 0;
    public int second_phase_overlay_texture_coord_x = 0;
    public int second_phase_overlay_texture_coord_y = 0;

    public double threshold_phase = 0.0;

    public boolean health_bar_before_overlay = true;
}

package com.nakuring.enhanced_boss_bars.util;

import net.minecraft.text.Text;

public record BossBarKey(String value, Type type) {

    public String id() {
        return Text.translatable(this.value).getString();
    }

    public enum Type {
        translation,
        contains,
        color
    }

}
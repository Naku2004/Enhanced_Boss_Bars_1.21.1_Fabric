package com.nakuring.enhanced_boss_bars.implementation;

import com.nakuring.enhanced_boss_bars.interfaces.Platform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatform implements Platform {

    @Override
    public Path configFile() {
        return FabricLoader.getInstance()
                .getConfigDir()
                .resolve("enhanced-boss-bars.json");
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

}
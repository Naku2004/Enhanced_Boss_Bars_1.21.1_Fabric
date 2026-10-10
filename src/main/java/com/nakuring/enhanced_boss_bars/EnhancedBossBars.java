package com.nakuring.enhanced_boss_bars;

import com.nakuring.enhanced_boss_bars.config.ConfigManager;
import com.nakuring.enhanced_boss_bars.implementation.FabricPlatform;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancedBossBars implements ModInitializer {
	public static final String MOD_ID = "enhanced_boss_bars";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        ConfigManager.initialize(new FabricPlatform());

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new BossBarsReloadListener());
	}

    public static String getModName(String modId) {
        return FabricLoader.getInstance()
                .getModContainer(modId)
                .map(mod -> mod.getMetadata().getName())
                .orElse(modId);
    }

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}

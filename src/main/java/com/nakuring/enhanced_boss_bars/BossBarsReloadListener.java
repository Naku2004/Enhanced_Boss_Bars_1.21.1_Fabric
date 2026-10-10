package com.nakuring.enhanced_boss_bars;


import com.nakuring.enhanced_boss_bars.bars_loader.BossBarsLoader;
import com.nakuring.enhanced_boss_bars.implementation.FabricPlatform;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBarsReloadListener
        implements  SimpleSynchronousResourceReloadListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger("Enhanced Boss Bars");

    @Override
    public Identifier getFabricId() {
        return Identifier.of("enhanced_boss_bars", "bossbars");
    }

    @Override
    public void reload(
            ResourceManager manager
    ) {

        LOGGER.info("BossBarsReloadListener reloading...");

        BossBarsManager.clear();

        BossBarsLoader.load(
                manager,
                new FabricPlatform()
        );
    }

}
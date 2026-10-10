package com.nakuring.enhanced_boss_bars.implementation;

import com.nakuring.enhanced_boss_bars.render.interfaces.BossBar;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

import java.util.*;

public class FabricBossBars implements BossBars {

    private final Map<UUID, ClientBossBar> client_boss_bars;

    public FabricBossBars(Map<UUID, ClientBossBar> client_boss_bars) {
        this.client_boss_bars = client_boss_bars;
    }

    @Override
    public Iterable<BossBar> getBossBars(int limit) {

        List<BossBar> result = new ArrayList<>();

        int count = 0;

        for (ClientBossBar bar : client_boss_bars.values()) {
            if (count >= limit) break;

            result.add(
                    new BossBar(
                            bar.getUuid(),
                            getKey(bar),
                            bar.getColor().getName(),
                            bar.getPercent(),
                            bar.getName().getString()
                    )
            );
            count++;
        }

        return result;
    }

    @Override
    public Set<UUID> uuids() {
        return client_boss_bars.keySet();
    }

    private BossBarKey getKey(ClientBossBar bar) {
        Text name = bar.getName();

        if (name.getContent() instanceof TranslatableTextContent translation) {
            return new BossBarKey(
                    translation.getKey(),
                    BossBarKey.Type.translation
            );
        }

        return new BossBarKey(
                name.getString(),
                BossBarKey.Type.contains
        );
    }
}
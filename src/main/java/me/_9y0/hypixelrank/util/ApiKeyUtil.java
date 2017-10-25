package me._9y0.hypixelrank.util;

import me._9y0.hypixelrank.HypixelRankPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApiKeyUtil {

    private static final List<UUID> API_KEYS = new ArrayList<>();
    private static int index = 0;

    public static UUID getNextApiKey() {
        UUID apiKey = API_KEYS.get(index++);
        if (index >= API_KEYS.size()) {
            index = 0;
        }

        return apiKey;
    }

    static {
        HypixelRankPlugin.getPlugin(HypixelRankPlugin.class).getConfig().getStringList("apiKeys").forEach(
                uuidString -> API_KEYS.add(UUID.fromString(uuidString)));
    }
}

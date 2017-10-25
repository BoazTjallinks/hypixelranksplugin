package me._9y0.hypixelrank.rank;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HypixelPlayerManager {

    private static final Map<UUID, HypixelPlayer> PLAYERS = new HashMap<>();

    public static HypixelPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    private static HypixelPlayer getPlayer(UUID uuid) {
        return PLAYERS.get(uuid);
    }

    public static void addPlayer(HypixelPlayer player) {
        PLAYERS.put(player.getPlayer().getUniqueId(), player);
    }

    public static void removePlayer(Player player) {
        PLAYERS.remove(player.getUniqueId());
    }
}

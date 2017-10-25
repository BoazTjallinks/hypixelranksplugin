package me._9y0.hypixelrank.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me._9y0.hypixelrank.HypixelRankPlugin;
import me._9y0.hypixelrank.rank.HypixelPlayer;
import me._9y0.hypixelrank.rank.HypixelPlayerManager;
import me._9y0.hypixelrank.rank.Rank;
import me._9y0.hypixelrank.util.ApiKeyUtil;
import me._9y0.hypixelrank.util.GsonUtil;
import me._9y0.hypixelrank.util.HttpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import java.io.InputStream;
import java.io.InputStreamReader;

public class PlayerListener implements Listener {

    private static final String URL = "https://api.hypixel.net/player?key=%s&uuid=%s";

    private final HypixelRankPlugin plugin;

    public PlayerListener(HypixelRankPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            InputStream input = HttpUtil.get(String.format(URL, ApiKeyUtil.getNextApiKey(), event.getPlayer().getUniqueId()));
            if (input == null) {
                event.getPlayer().sendMessage(ChatColor.RED + "Something went wrong while loading your rank. Contact a admin.");
                return;
            }
            JsonElement response = GsonUtil.PARSER.parse(new InputStreamReader(input));
            if (response.isJsonObject() && response.getAsJsonObject().get("success").getAsBoolean()) {
                JsonObject playerObject = response.getAsJsonObject().get("player").getAsJsonObject();

                Rank rank = Rank.getRank(playerObject);
                ChatColor rankPlusColor = ChatColor.RED;
                if (playerObject.has("rankPlusColor")) {
                    rankPlusColor = ChatColor.valueOf(playerObject.get("rankPlusColor").getAsString());
                }
                HypixelPlayer player = new HypixelPlayer(event.getPlayer(), rank, rankPlusColor);
                HypixelPlayerManager.addPlayer(player);

                Team team = event.getPlayer().getScoreboard().getTeam(player.getPrefix());
                team.setSuffix(ChatColor.RESET.toString());
                if (team == null) {
                    team = event.getPlayer().getScoreboard().registerNewTeam(player.getPrefix());
                }
                team.setPrefix(player.getPrefix());
                team.addEntry(event.getPlayer().getName());
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Something went wrong while loading your rank. Contact a admin.");
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HypixelPlayerManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        HypixelPlayer player = HypixelPlayerManager.getPlayer(event.getPlayer());
        if (player == null) {
            return;
        }

        event.setCancelled(true);
        Bukkit.getOnlinePlayers().forEach(online ->
                online.sendMessage(player.getPrefix() + player.getPlayer().getName() + (player.getRank().isWhiteChat() ? ChatColor.WHITE : ChatColor.GRAY) + ": " + event.getMessage()));
    }
}
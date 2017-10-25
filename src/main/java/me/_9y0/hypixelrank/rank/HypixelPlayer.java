package me._9y0.hypixelrank.rank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HypixelPlayer {

    private final Player player;
    private final Rank rank;
    private final ChatColor rankPlusColor;

    public HypixelPlayer(Player player, Rank rank, ChatColor rankPlusColor) {
        this.player = player;
        this.rank = rank;
        this.rankPlusColor = rankPlusColor;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPrefix() {
        return rank == Rank.MVP_PLUS ? String.format(rank.getPrefix(), rankPlusColor.toString()) : rank.getPrefix();
    }

    public Rank getRank() {
        return rank;
    }
}

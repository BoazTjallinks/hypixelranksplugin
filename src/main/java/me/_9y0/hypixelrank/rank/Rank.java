package me._9y0.hypixelrank.rank;

import com.google.gson.JsonObject;
import org.bukkit.ChatColor;

import java.util.Arrays;

public enum Rank {

    NONE(ChatColor.GRAY.toString(), false), //
    VIP(ChatColor.GREEN + "[VIP] ", true), //
    VIP_PLUS(ChatColor.GREEN + "[VIP" + ChatColor.GOLD + "+" + ChatColor.GREEN + "] ", true), //
    MVP(ChatColor.AQUA + "[MVP] ", true), //
    MVP_PLUS(ChatColor.AQUA + "[MVP%s+" + ChatColor.AQUA + "] ", true), //
    YOUTUBER(ChatColor.GOLD + "[YT] ", true), //
    HELPER(ChatColor.BLUE + "[HELPER] ", true), //
    MODERATOR(ChatColor.DARK_GREEN + "[MOD] ", true), //
    ADMIN(ChatColor.RED + "[ADMIN] ", true) //
    ;

    private final String prefix;
    private final boolean whiteChat;

    Rank(String prefix, boolean whiteChat) {
        this.prefix = prefix;
        this.whiteChat = whiteChat;
    }

    public static Rank getRank(JsonObject playerObject) {
        if (playerObject.has("rank")) {
            return Arrays.stream(Rank.values()).filter(rank -> rank.name().equals(playerObject.get("rank").getAsString())).findFirst().orElse(Rank.NONE);
        }

        if (playerObject.has("newPackageRank")) {
            return Arrays.stream(Rank.values()).filter(rank -> rank.name().equals(playerObject.get("newPackageRank").getAsString())).findFirst().orElse(Rank.NONE);
        }

        if (playerObject.has("packageRank")) {
            return Arrays.stream(Rank.values()).filter(rank -> rank.name().equals(playerObject.get("packageRank").getAsString())).findFirst().orElse(Rank.NONE);
        }

        return Rank.NONE;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isWhiteChat() {
        return whiteChat;
    }
}

package me._9y0.hypixelrank;

import me._9y0.hypixelrank.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelRankPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }
}

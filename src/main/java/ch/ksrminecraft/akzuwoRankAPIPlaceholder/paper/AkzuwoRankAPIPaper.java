package ch.ksrminecraft.akzuwoRankAPIPlaceholder.paper;

import ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils.RankAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AkzuwoRankAPIPaper extends JavaPlugin {

    private RankAPI rankAPI;
    private Logger logger;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        logger = LoggerFactory.getLogger(getLogger().getName());
        try {
            rankAPI = new RankAPI(getDataFolder().toPath(), logger);
        } catch (Exception e) {
            getLogger().severe("Failed to load config: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new AkzuwoRankPlaceholder(this, rankAPI).register();
        } else {
            getLogger().warning("PlaceholderAPI not found, placeholder will not work");
        }
    }

    public RankAPI getRankAPI() {
        return rankAPI;
    }
}

package ch.ksrminecraft.akzuwoRankAPIPlaceholder.paper;

import ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils.RankAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class AkzuwoRankPlaceholder extends PlaceholderExpansion {

    private final AkzuwoRankAPIPaper plugin;
    private final RankAPI api;

    public AkzuwoRankPlaceholder(AkzuwoRankAPIPaper plugin, RankAPI api) {
        this.plugin = plugin;
        this.api = api;
    }

    @Override
    public String getIdentifier() {
        // Use a dedicated identifier to avoid conflicts with other plugins
        // that might already register "akzuwoextension" placeholders.
        return "akzuwo_rankpoints";
    }

    @Override
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }
        if (identifier.equalsIgnoreCase("points")) {
            return String.valueOf(api.getPoints(player.getUniqueId()));
        }
        return null;
    }
}

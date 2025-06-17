package ch.ksrminecraft.akzuwoRankAPIPlaceholder;

import ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils.RankAPI;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.william278.papiproxybridge.api.PlaceholderAPI;
import net.william278.papiproxybridge.api.PlaceholderSupplier;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Plugin(id = "akzuwoextension", name = "AkzuwoRankAPIPlaceholder", version = "1.0")
public class AkzuwoRankAPIPlaceholder {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private RankAPI rankAPI;

    @Inject
    public AkzuwoRankAPIPlaceholder(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try {
            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }
            rankAPI = new RankAPI(dataDirectory, logger);
        } catch (Exception e) {
            logger.error("Failed to load config", e);
            return;
        }

        // Register placeholder with PAPIProxyBridge
        PlaceholderAPI.getInstance().registerPlaceholder("akzuwoextension_points", new PlaceholderSupplier() {
            @Override
            public String onRequest(UUID uuid) {
                return String.valueOf(rankAPI.getPoints(uuid));
            }
        });

        logger.info("AkzuwoRankAPIPlaceholder loaded");
    }
}

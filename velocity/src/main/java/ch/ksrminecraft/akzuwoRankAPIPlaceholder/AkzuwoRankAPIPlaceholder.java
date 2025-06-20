package ch.ksrminecraft.akzuwoRankAPIPlaceholder;

import ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils.RankAPI;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ch.ksrminecraft.akzuwoRankAPIPlaceholder.BuildConstants;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(id = "akzuwoextension", name = "AkzuwoRankAPIPlaceholder", version = BuildConstants.VERSION)
public class AkzuwoRankAPIPlaceholder {

    private final ProxyServer server;
    private final Path dataDirectory;
    private final Logger logger;
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



        if (rankAPI.isCommandsEnabled()) {
            CommandManager manager = server.getCommandManager();
            manager.register(
                    manager.metaBuilder("akzuwoextension").plugin(this).build(),
                    new AkzuwoExtensionCommand(server, rankAPI)
            );
        }

        logger.info("AkzuwoRankAPIPlaceholder loaded");
    }
}

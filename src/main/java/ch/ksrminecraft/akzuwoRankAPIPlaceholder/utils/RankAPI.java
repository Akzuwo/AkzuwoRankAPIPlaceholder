package ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils;

import ch.ksrminecraft.RankPointsAPI.PointsAPI;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class RankAPI {
    private final PointsAPI api;

    public RankAPI(Path dataDirectory, Logger logger) throws Exception {
        Path configFile = dataDirectory.resolve("config.yml");
        if (Files.notExists(configFile)) {
            try (InputStream in = RankAPI.class.getClassLoader().getResourceAsStream("config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile);
                }
            }
        }

        Yaml yaml = new Yaml();
        Map<String, Object> config;
        try (InputStream in = Files.newInputStream(configFile)) {
            config = yaml.load(in);
        }

        Map<String, Object> db = (Map<String, Object>) config.get("database");
        String url = (String) db.get("url");
        String username = (String) db.get("username");
        String password = (String) db.get("password");
        boolean debug = Boolean.parseBoolean(String.valueOf(db.getOrDefault("debug", false)));

        this.api = new PointsAPI(url, username, password, logger, debug);
    }

    public int getPoints(UUID uuid) {
        return api.getPoints(uuid);
    }
}

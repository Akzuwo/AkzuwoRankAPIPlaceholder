package ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils;

import ch.ksrminecraft.RankPointsAPI.PointsAPI;

import java.util.logging.Logger;

import static javax.sql.rowset.spi.SyncFactory.getLogger;

public class RankAPI {
    Logger logger = getLogger(); // or any other logger
    PointsAPI api = new PointsAPI(
            "jdbc:mysql://host:port/database",
            "username",
            "password",
            logger,
            true // enable debug
    );
}

package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import org.slf4j.Logger;

import java.util.ArrayList;

public class MonitorManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public static final ArrayList<String> players = new ArrayList<>();

    public static void init() {
        LOGGER.info("Database Initializing");
        players.add("tangsu99");
    }
}

package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import org.slf4j.Logger;

import java.util.ArrayList;

public class MonitorManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public static final ArrayList<String> playersName = new ArrayList<>();

    public static void init() {
        LOGGER.info("Monitor Initializing");
        DatabaseManager.queryPlayersName(playersName);
    }
}

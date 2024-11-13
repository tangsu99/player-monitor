package ink.fsp.playerMonitor;

import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.monitors.TickMonitor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMonitor implements ModInitializer {
    public static final String MOD_ID = "player-monitor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing PlayerMonitor");
        DatabaseManager.init();
//        PlayerMoveCallback.EVENT.register(new ConstantlyMonitor());
//        PlayerMoveCallback.EVENT.register(new RegionMonitor());
        ServerTickEvents.END_SERVER_TICK.register(new TickMonitor());
    }
}

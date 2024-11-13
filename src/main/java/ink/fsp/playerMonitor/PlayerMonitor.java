package ink.fsp.playerMonitor;

import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import ink.fsp.playerMonitor.monitors.ConstantlyMonitor;
import ink.fsp.playerMonitor.monitors.RegionMonitor;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMonitor implements ModInitializer {
    public static final String MOD_ID = "player-monitor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing PlayerMonitor");
        PlayerMoveCallback.EVENT.register(new ConstantlyMonitor());
        PlayerMoveCallback.EVENT.register(new RegionMonitor());
    }
}

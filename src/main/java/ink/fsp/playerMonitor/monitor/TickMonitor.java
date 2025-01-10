package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.util.Date;

public class TickMonitor implements ServerTickEvents.EndTick {
    private long ticks = 0;
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
//        if (ticks++ % 100 == 0) {
//            minecraftServer.getPlayerManager().getPlayerList().forEach(player -> DatabaseManager.insertTrackn(
//                    player.getGameProfile().getId(),
//                    player.getX(),
//                    player.getY(),
//                    player.getZ(),
//                    player.getWorld().getDimensionEntry().getIdAsString(),
//                    new Date()
//            ));
//            ticks = 0;
//        }
    }
}

package ink.fsp.playerMonitor.monitors;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.util.Date;

public class TickMonitor implements ServerTickEvents.EndTick {
    private int ticks = 0;
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        if (ticks++ % 100 == 0) {
            minecraftServer.getPlayerManager().getPlayerList().forEach(player -> {
                DatabaseManager.insertTrackn(
                        player.getGameProfile().getName(),
                        (int) player.getX(),
                        (int) player.getY(),
                        (int) player.getZ(),
                        player.getWorld().getDimensionEntry().getIdAsString(),
                        new Date()
                );
            });
        }
    }
}

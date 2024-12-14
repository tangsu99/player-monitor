package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;

import java.util.Date;

public class PlayerConnectionEvents {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    public void register() {
        ServerPlayConnectionEvents.JOIN.register(this::onPlayJoin);
        ServerPlayConnectionEvents.DISCONNECT.register(this::onPlayLeave);
    }

    private void onPlayJoin(ServerPlayNetworkHandler handler, PacketSender packetSender, MinecraftServer minecraftServer) {
        MonitorManager.addPlayer(handler.getPlayer());
    }

    private void onPlayLeave(ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer minecraftServer) {
        var player = serverPlayNetworkHandler.getPlayer();
        DatabaseManager.UpdatePlayerLastByUuid(player.getUuid(), player.getX(), player.getY(), player.getZ(), new Date());
    }
}

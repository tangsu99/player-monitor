package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;

public class PlayerJoinEvent {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    public void register() {
        ServerPlayConnectionEvents.JOIN.register(this::onPlayReady);
    }

    private void onPlayReady(ServerPlayNetworkHandler handler, PacketSender packetSender, MinecraftServer minecraftServer) {
        LOGGER.info(handler.getPlayer().getName().getString());
        MonitorManager.playersName.add(handler.getPlayer().getName().getString());
    }
}

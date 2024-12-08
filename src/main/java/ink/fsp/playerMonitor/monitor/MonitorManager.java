package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.PlayerItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MonitorManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public static ArrayList<PlayerItem> players = new ArrayList<>();

    public static void init() {
        LOGGER.info("Monitor Initializing");
        // 启动先查玩家数据
        players = DatabaseManager.selectPlayers();
    }

    public static void addPlayer(ServerPlayerEntity player) {
        UUID uuid = player.getGameProfile().getId();
        if (inPlayers(uuid)) {  // 在
            // 更新数据
        }else {     // 不在
            // 写入数据库
        }
    }

    // 返回所有在数据库的玩家的玩家名
    public static List<String> getPlayersName() {
        return players.stream().map((item) -> item.playername).distinct().collect(Collectors.toList());
    }

    private static boolean inPlayers(UUID uuid) {
        for (PlayerItem item : players) {
            if (item.uuid.equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}

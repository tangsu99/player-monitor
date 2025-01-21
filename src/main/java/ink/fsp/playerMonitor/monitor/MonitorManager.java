package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.PlayerItem;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MonitorManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public static ArrayList<PlayerItem> players = new ArrayList<>();
    public static ArrayList<RegionItem> regions = new ArrayList<>();

    public static void init() {
        LOGGER.info("Monitor Initializing");
        // 启动先查玩家数据
        players = DatabaseManager.selectPlayers();
        // 查区域
        regions = DatabaseManager.selectRegions();
    }

    public static void addPlayer(ServerPlayerEntity player) {
        UUID uuid = player.getGameProfile().getId();
        if (inPlayers(uuid)) {  // 在
            // 更新数据
            DatabaseManager.UpdatePlayerNameByUuid(player.getGameProfile().getName(), uuid, new Date());
        }else {     // 不在
            // 写入数据库
            PlayerItem playerItem = PlayerItem.getPlayerItem(player);
            DatabaseManager.insertPlayers(playerItem);
            players.add(playerItem);
        }
    }

    public static int addRegions(RegionItem regionItem) {
        // 写入数据库
        var result = DatabaseManager.insertRegion(regionItem);
        regions.add(regionItem);
        return result;
    }

    // 返回所有在数据库的玩家的玩家名
    public static List<String> getPlayersName() {
        return players.stream().map((item) -> item.playername).distinct().collect(Collectors.toList());
    }

    public static List<String> getRegionName() {
        return regions.stream().map((item) -> item.regionName).distinct().collect(Collectors.toList());
    }

    private static boolean inPlayers(UUID uuid) {
        for (PlayerItem item : players) {
            if (item.uuid.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static UUID getUUID(String name) {
        for (PlayerItem item : players) {
            if (item.playername.equals(name)) {
                return item.uuid;
            }
        }
        return null;
    }
}

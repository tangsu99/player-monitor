package ink.fsp.playerMonitor.database.ResultItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Date;
import java.util.UUID;

public class PlayerItem {
    public String playername;
    public UUID uuid;
    public double x;
    public double y;
    public double z;
    // 第一次上线
    public Date firstJoinDatetime;
    // 最后一次上线
    public Date lastJoinDatetime;
    // 最后一次下线
    public Date lastLeaveDatetime;

    public PlayerItem(String playername, String uuid, double x, double y, double z, Date firstJoinDatetime, Date lastJoinDatetime, Date lastLeaveDatetime) {
        this.playername = playername;
        this.uuid = UUID.fromString(uuid);
        this.x = x;
        this.y = y;
        this.z = z;
        this.firstJoinDatetime = firstJoinDatetime;
        this.lastJoinDatetime = lastJoinDatetime;
        this.lastLeaveDatetime = lastLeaveDatetime;
    }

    public static PlayerItem getPlayerItem(String playername, String uuid, double x, double y, double z, Date firstJoinDatetime, Date lastJoinDatetime, Date lastLeaveDatetime) {
        return new PlayerItem(playername, uuid, x, y, z, firstJoinDatetime, lastJoinDatetime, lastLeaveDatetime);
    }

    public static PlayerItem getPlayerItem(ServerPlayerEntity player) {
        Date current = new Date();
        return new PlayerItem(player.getGameProfile().getName(),
                player.getUuidAsString(),
                player.getX(),
                player.getY(),
                player.getZ(),
                current,
                current,
                current
        );
    }

    @Override
    public String toString() {
        return "PlayerItem{" +
                "playername='" + playername + '\'' +
                ", uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", firstJoinDatetime=" + firstJoinDatetime +
                ", lastJoinDatetime=" + lastJoinDatetime +
                ", lastLeaveDatetime=" + lastLeaveDatetime +
                '}';
    }
}

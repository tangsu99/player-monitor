package ink.fsp.playerMonitor.database.ResultItem;

import java.util.Date;
import java.util.UUID;

public class PlayerItem {
    public String playername;
    public UUID uuid;
    public int x;
    public int y;
    public int z;
    public Date firstDatetime;
    public Date lastDatetime;

    public PlayerItem(String playername, String uuid, int x, int y, int z, Date firstDatetime, Date lastDatetime) {
        this.playername = playername;
        this.uuid = UUID.fromString(uuid);
        this.x = x;
        this.y = y;
        this.z = z;
        this.firstDatetime = firstDatetime;
        this.lastDatetime = lastDatetime;
    }

    public static PlayerItem getPlayerItem(String playername, String uuid, int x, int y, int z, Date firstDatetime, Date lastDatetime) {
        return new PlayerItem(playername, uuid, x, y, z, firstDatetime, lastDatetime);
    }

    @Override
    public String toString() {
        return "PlayerItem{" +
                "playername='" + playername + '\'' +
                ", uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", firstDatetime=" + firstDatetime +
                ", lastDatetime=" + lastDatetime +
                '}';
    }
}

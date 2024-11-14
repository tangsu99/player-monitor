package ink.fsp.playerMonitor.database.ResultItem;

import ink.fsp.playerMonitor.utils.DimensionTranslation;

public class TracknItem {
    public String playername;
    public int x;
    public int y;
    public int z;
    public String dimension;

    public TracknItem(String playername, int x, int y, int z, String dimension) {
        this.playername = playername;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public static TracknItem getTracknItem(String playername, int x, int y, int z, String dimension) {
        return new TracknItem(playername, x, y, z, dimension);
    }

    @Override
    public String toString() {
        return playername + ": [x: " + x + ", y: " + y + ", z: " + z + "] in " + DimensionTranslation.translation(dimension);
    }
}

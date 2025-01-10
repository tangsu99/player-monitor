package ink.fsp.playerMonitor.database.ResultItem;

import ink.fsp.playerMonitor.utils.DimensionTranslation;

import java.util.Date;

public class TrackerItem {
    public String playername;
    public String regionName;
    public double x;
    public double y;
    public double z;
    public String dimension;
    public Date datetime;

    public TrackerItem(String playername, String regionName, double x, double y, double z, String dimension, Date datetime) {
        this.playername = playername;
        this.regionName = regionName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.datetime = datetime;
    }

    public static TrackerItem getTracknItem(String playername, String regionName, double x, double y, double z, String dimension, Date datetime) {
        return new TrackerItem(playername, regionName, x, y, z, dimension, datetime);
    }

    @Override
    public String toString() {
        return playername + ": [x: " + x + ", y: " + y + ", z: " + z + "] in " + DimensionTranslation.translation(dimension) + " at " + datetime;
    }
}

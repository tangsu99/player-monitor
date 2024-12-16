package ink.fsp.playerMonitor.database.ResultItem;

import net.minecraft.util.math.Vec3d;

import java.util.Date;

public class RegionItem {
    public Vec3d start;
    public Vec3d end;
    public String world;
    public String regionName;
    public String createdBy;
    public Date createdOn;

    public RegionItem(Vec3d start, Vec3d end, String world, String regionName, String createdBy, Date createdOn) {
        this.start = start;
        this.end = end;
        this.world = world;
        this.regionName = regionName;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }
    public static RegionItem getRegionItem(Vec3d start, Vec3d end, String world, String regionName, String createdBy) {
        return new RegionItem(start, end, world, regionName, createdBy, new Date());
    }
}

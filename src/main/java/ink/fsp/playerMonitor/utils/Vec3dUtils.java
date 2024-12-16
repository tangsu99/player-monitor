package ink.fsp.playerMonitor.utils;

import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.util.math.Vec3d;

public class Vec3dUtils {

    /**
     * 检查点是否在由两个点定义的坐标范围内。
     *
     * @param point 要检查的点
     * @param region 检查区
     * @return 如果点在范围内返回true，否则返回false
     */
    public static boolean isPointInRange(Vec3d point, RegionItem region) {
        return isPointInRange(point, region.start, region.end);
    }

    /**
     * 检查点是否在由两个点定义的坐标范围内。
     *
     * @param point 要检查的点
     * @param start 检查区起始
     * @param end 检查区结束
     * @return 如果点在范围内返回true，否则返回false
     */
    public static boolean isPointInRange(Vec3d point, Vec3d start, Vec3d end) {
        double minX = Math.min(start.x, end.x);
        double maxX = Math.max(start.x, end.x);
        double minY = Math.min(start.y, end.y);
        double maxY = Math.max(start.y, end.y);
        double minZ = Math.min(start.z, end.z);
        double maxZ = Math.max(start.z, end.z);

        return point.x >= minX && point.x <= maxX &&
                point.y >= minY && point.y <= maxY &&
                point.z >= minZ && point.z <= maxZ;
    }
}
package ink.fsp.playerMonitor.utils;

import net.minecraft.util.math.Vec3d;

public class LineUtils {
    public static Vec3d[] getPointsOnLine(Vec3d start, Vec3d end, int points) {
        Vec3d[] linePoints = new Vec3d[points];
        double step = 1.0 / (points - 1);
        for (int i = 0; i < points; i++) {
            double t = i * step;
            double x = start.x + t * (end.x - start.x);
            double y = start.y + t * (end.y - start.y);
            double z = start.z + t * (end.z - start.z);
            linePoints[i] = new Vec3d(x, y, z);
        }
        return linePoints;
    }
}
package ink.fsp.playerMonitor.particle;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.utils.LineUtils;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;

import java.util.ArrayList;

public class ParticleUtils {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public static void drawLine(ServerWorld world, Vec3d start, Vec3d end, int points, int duration) {
        Vec3d[] linePoints = LineUtils.getPointsOnLine(start, end, points);
        for (Vec3d point : linePoints) {
            ParticleManager.addParticle(new ParticleItem(world, point, duration, new DustParticleEffect(DustParticleEffect.RED, 0.5F)));
        }
    }

    public static void drawParticleBox(ServerWorld world, Vec3d point, int width, int height, int length, int quantity, int duration) {
        ArrayList<Vec3d> points = new ArrayList<>();
        points.add(new Vec3d(point.x - (double) width / 2, point.y - (double) height / 2, point.z - (double) length / 2));
        points.add(new Vec3d(point.x + (double) width / 2, point.y - (double) height / 2, point.z - (double) length / 2));
        points.add(new Vec3d(point.x + (double) width / 2, point.y + (double) height / 2, point.z - (double) length / 2));
        points.add(new Vec3d(point.x - (double) width / 2, point.y + (double) height / 2, point.z - (double) length / 2));
        points.add(new Vec3d(point.x - (double) width / 2, point.y - (double) height / 2, point.z + (double) length / 2));
        points.add(new Vec3d(point.x + (double) width / 2, point.y - (double) height / 2, point.z + (double) length / 2));
        points.add(new Vec3d(point.x + (double) width / 2, point.y + (double) height / 2, point.z + (double) length / 2));
        points.add(new Vec3d(point.x - (double) width / 2, point.y + (double) height / 2, point.z + (double) length / 2));

        drawParticleBox(world, points, quantity, duration);
    }

    public static void drawParticleBox(ServerWorld world, Vec3d start, Vec3d end, int quantity, int duration) {
        ArrayList<Vec3d> points = new ArrayList<>();
        double minX = Math.min(start.x, end.x);
        double maxX = Math.max(start.x, end.x);
        double minY = Math.min(start.y, end.y);
        double maxY = Math.max(start.y, end.y);
        double minZ = Math.min(start.z, end.z);
        double maxZ = Math.max(start.z, end.z);

        points.add(new Vec3d(minX, minY, minZ));    // 1
        points.add(new Vec3d(minX, minY, maxZ));    // 2
        points.add(new Vec3d(minX, maxY, maxZ));    // 4
        points.add(new Vec3d(minX, maxY, minZ));    // 3
        points.add(new Vec3d(maxX, minY, minZ));    // 5
        points.add(new Vec3d(maxX, minY, maxZ));    // 6
        points.add(new Vec3d(maxX, maxY, maxZ));    // 8
        points.add(new Vec3d(maxX, maxY, minZ));    // 7

        drawParticleBox(world, points, quantity, duration);
    }

    private static boolean drawParticleBox(ServerWorld world, ArrayList<Vec3d> points, int quantity, int duration) {
        if (points.isEmpty()) {
            return false;
        }
        if (points.size() == 8) {
            drawLine(world, points.get(0), points.get(1), quantity, duration);
            drawLine(world, points.get(1), points.get(2), quantity, duration);
            drawLine(world, points.get(2), points.get(3), quantity, duration);
            drawLine(world, points.get(3), points.get(0), quantity, duration);

            drawLine(world, points.get(4), points.get(5), quantity, duration);
            drawLine(world, points.get(5), points.get(6), quantity, duration);
            drawLine(world, points.get(6), points.get(7), quantity, duration);
            drawLine(world, points.get(7), points.get(4), quantity, duration);

            drawLine(world, points.get(0), points.get(4), quantity, duration);
            drawLine(world, points.get(1), points.get(5), quantity, duration);
            drawLine(world, points.get(2), points.get(6), quantity, duration);
            drawLine(world, points.get(3), points.get(7), quantity, duration);
            return true;
        }
        else {
            drawLine(world, points.getFirst(), points.get(7), quantity, duration);
            return false;
        }
    }
}

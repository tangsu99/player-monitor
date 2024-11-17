package ink.fsp.playerMonitor.particle;

import ink.fsp.playerMonitor.utils.LineUtils;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class ParticleUtils {
    public static void drawLine(ServerWorld world, Vec3d start, Vec3d end, int points, int duration) {
        Vec3d[] linePoints = LineUtils.getPointsOnLine(start, end, points);
        for (Vec3d point : linePoints) {
            ParticleManager.addParticle(new ParticleItem(world, point, duration, new DustParticleEffect(DustParticleEffect.RED, 0.5F)));
        }
    }

    public static void drawParticleBox(ServerWorld world, Vec3d point, int width, int height, int length, int points, int duration) {
        Vec3d point1 = new Vec3d(point.x - (double) width / 2, point.y - (double) height / 2, point.z - (double) length / 2);
        Vec3d point2 = new Vec3d(point.x + (double) width / 2, point.y - (double) height / 2, point.z - (double) length / 2);
        Vec3d point3 = new Vec3d(point.x + (double) width / 2, point.y + (double) height / 2, point.z - (double) length / 2);
        Vec3d point4 = new Vec3d(point.x - (double) width / 2, point.y + (double) height / 2, point.z - (double) length / 2);
        Vec3d point5 = new Vec3d(point.x - (double) width / 2, point.y - (double) height / 2, point.z + (double) length / 2);
        Vec3d point6 = new Vec3d(point.x + (double) width / 2, point.y - (double) height / 2, point.z + (double) length / 2);
        Vec3d point7 = new Vec3d(point.x + (double) width / 2, point.y + (double) height / 2, point.z + (double) length / 2);
        Vec3d point8 = new Vec3d(point.x - (double) width / 2, point.y + (double) height / 2, point.z + (double) length / 2);

        drawLine(world, point1, point2, points, duration);
        drawLine(world, point2, point3, points, duration);
        drawLine(world, point3, point4, points, duration);
        drawLine(world, point4, point1, points, duration);

        drawLine(world, point5, point6, points, duration);
        drawLine(world, point6, point7, points, duration);
        drawLine(world, point7, point8, points, duration);
        drawLine(world, point8, point5, points, duration);

        drawLine(world, point1, point5, points, duration);
        drawLine(world, point2, point6, points, duration);
        drawLine(world, point3, point7, points, duration);
        drawLine(world, point4, point8, points, duration);
    }
}

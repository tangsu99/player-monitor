package ink.fsp.playerMonitor.particle;

import ink.fsp.playerMonitor.utils.LineUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class ParticleLine {
    public static void drawLine(ServerWorld world, Vec3d start, Vec3d end, ParticleEffect particle, int points) {
        Vec3d[] linePoints = LineUtils.getPointsOnLine(start, end, points);
        for (Vec3d point : linePoints) {
            world.spawnParticles(particle, point.x, point.y, point.z, 1, 0, 0, 0, 0);
        }
    }
}
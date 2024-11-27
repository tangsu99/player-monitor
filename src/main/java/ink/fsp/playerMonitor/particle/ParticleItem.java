package ink.fsp.playerMonitor.particle;

import ink.fsp.playerMonitor.PlayerMonitor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class ParticleItem {
    private final ServerWorld world;
    private final Vec3d position;
    private final ParticleEffect particleEffect;
    private int tickCount = 0;
    private final int duration;

    public ParticleItem(ServerWorld world, Vec3d position, int duration) {
        this.world = world;
        this.position = position;
        this.particleEffect = ParticleManager.PARTICLE_TYPE;
        this.duration = duration * 20;
    }

    public ParticleItem(ServerWorld world, Vec3d position, int duration, ParticleEffect particleEffect) {
        this.world = world;
        this.position = position;
        this.duration = duration * 20;
        this.particleEffect = particleEffect;
    }

    public boolean spawnParticle() {
        if (tickCount++ < duration) {
            if (tickCount % 5 == 0) {
                world.spawnParticles(particleEffect, position.x, position.y, position.z, 1, 0, 0, 0, 0);
            }
            return true;
        }
        return false;
    }
}
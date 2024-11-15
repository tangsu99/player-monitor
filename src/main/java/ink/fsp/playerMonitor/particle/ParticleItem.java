package ink.fsp.playerMonitor.particle;

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
        this.duration = duration;
    }

    public ParticleItem(ServerWorld world, Vec3d position, ParticleEffect particleEffect, int duration) {
        this.world = world;
        this.position = position;
        this.particleEffect = particleEffect;
        this.duration = duration;
    }

    public boolean spawnParticle() {
        if (tickCount++ < duration) {
            world.spawnParticles(particleEffect, position.x, position.y, position.z, 1, 0, 0, 0, 0);
            return true;
        }
        return false;
    }
}
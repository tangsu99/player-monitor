package ink.fsp.playerMonitor.particle;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

public class ParticleManager implements ServerTickEvents.EndTick {
    public final static ParticleEffect PARTICLE_TYPE = ParticleTypes.FLAME;
    private final static ArrayList<ParticleItem> particleQueue = new ArrayList<>();

    public static void addParticle(ParticleItem particleItem) {
        particleQueue.add(particleItem);
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        for (ParticleItem particleItem : particleQueue) {
            particleItem.spawnParticle();
        }
    }
}

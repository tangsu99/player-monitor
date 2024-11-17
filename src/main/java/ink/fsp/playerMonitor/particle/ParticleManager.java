package ink.fsp.playerMonitor.particle;

import ink.fsp.playerMonitor.PlayerMonitor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.util.ArrayList;

public class ParticleManager implements ServerTickEvents.EndTick {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public final static ParticleEffect PARTICLE_TYPE = ParticleTypes.FLAME;
    private final static ArrayList<ParticleItem> particleQueue = new ArrayList<>();

    public static void addParticle(ParticleItem particleItem) {
        particleQueue.add(particleItem);
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        particleQueue.removeIf(particleItem -> !particleItem.spawnParticle());
    }
}

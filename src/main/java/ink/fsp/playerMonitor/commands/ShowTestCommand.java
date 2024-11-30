package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.TracknItem;
import ink.fsp.playerMonitor.particle.ParticleItem;
import ink.fsp.playerMonitor.particle.ParticleManager;
import ink.fsp.playerMonitor.particle.ParticleUtils;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;

import java.util.ArrayList;



public class ShowTestCommand {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager
                .literal("showtest")
                .then(CommandManager
                        .argument("player", StringArgumentType.string())
                        .executes((ShowTestCommand::showtest))
                )
        );
    }
    private static int showtest(CommandContext<ServerCommandSource> context){
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player != null) {
            ArrayList<TracknItem> result = DatabaseManager.selectTrackn(context.getArgument("player", String.class));
            if (result != null && !result.isEmpty()) {
                result.forEach(tracknItem -> {
//                    source.sendMessage(
//                            Text.literal(tracknItem.toString())
//                    );
//                    ParticleManager.addParticle(new ParticleItem(context.getSource().getServer().getOverworld(), new Vec3d(tracknItem.x, tracknItem.y, tracknItem.z), 10000));
//                    ParticleManager.addParticle(
//                            new ParticleItem(
//                                    context.getSource().getServer().getOverworld(),
//                                    new Vec3d(tracknItem.x, tracknItem.y, tracknItem.z),
//                                    10,
//                                    new DustParticleEffect(DustParticleEffect.RED, 1F)
//                            )
//                    );
                    ParticleUtils.drawParticleBox(
                            context.getSource().getServer().getOverworld(), context.getSource().getEntity().getPos(),
                            10,
                            10,
                            10,
                            10,
                            10,
                            true
                    );
//                    ParticleManager.spawnParticle(context.getSource().getServer().getOverworld(), tracknItem.x, tracknItem.y, tracknItem.z);
                });
            }
        }
        return 1;
    }
}

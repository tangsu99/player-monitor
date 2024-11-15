package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.TracknItem;
import ink.fsp.playerMonitor.particle.ParticleItem;
import ink.fsp.playerMonitor.particle.ParticleManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;



public class ShowTestCommand {
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
            ArrayList<TracknItem> result = DatabaseManager.select(context.getArgument("player", String.class));
            if (result != null && !result.isEmpty()) {
                result.forEach(tracknItem -> {
                    source.sendMessage(
                            Text.literal(tracknItem.toString())
                    );
                    ParticleManager.addParticle(new ParticleItem(context.getSource().getServer().getOverworld(), new Vec3d(tracknItem.x, tracknItem.y, tracknItem.z), 10000));
//                    ParticleManager.spawnParticle(context.getSource().getServer().getOverworld(), tracknItem.x, tracknItem.y, tracknItem.z);
                });
            }
        }
        return 1;
    }
}

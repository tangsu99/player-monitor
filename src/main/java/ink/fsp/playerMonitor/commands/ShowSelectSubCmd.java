package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.commands.suggestions.PlayerSuggestionProvider;
import ink.fsp.playerMonitor.particle.ParticleUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowSelectSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
         return CommandManager.literal("show")
                 .then(CommandManager.argument("player", StringArgumentType.string())
                         .suggests(new PlayerSuggestionProvider())
                         .executes(context -> showSelectByPlayer(context.getSource(), StringArgumentType.getString(context, "player")))
                         .then(CommandManager.argument("time", IntegerArgumentType.integer())
                                 .executes(context -> showSelectByTime(context.getSource(), StringArgumentType.getString(context, "player"), IntegerArgumentType.getInteger(context, "time")))
                         )
                 );
    }

    private static int showSelectByPlayer(ServerCommandSource source, String player) {
        if (source.getPlayer() != null) {
            source.sendFeedback(() -> Text.of(player), false);
            ParticleUtils.drawParticleBox(
                    source.getServer().getOverworld(), source.getEntity().getPos(),
                    10,
                    10,
                    10,
                    10,
                    10,
                    true
            );
            return 1;
        }
        source.sendMessage(Text.of("此命令只能玩家执行"));
        return 0;
    }

    private static int showSelectByTime(ServerCommandSource source, String player, int time) {
        if (source.getPlayer() != null) {
            SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
            Date currentTime = new Date();
            Date targetTime = new Date(currentTime.getTime() - (time * 1000L * 60L));
            source.sendFeedback(() -> Text.of(player + ": " +ft.format(currentTime) + " " + ft.format(targetTime)), false);
            return 1;
        }
        source.sendMessage(Text.of("此命令只能玩家执行"));
        return 0;
    }
}

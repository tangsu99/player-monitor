package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.commands.arguments.PlayerArgumentType;
import ink.fsp.playerMonitor.commands.suggestions.PlayerSuggestionProvider;
import ink.fsp.playerMonitor.particle.ParticleUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class ShowSelectSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
         return CommandManager.literal("show")
                 .then(CommandManager.argument("player", new PlayerArgumentType())
                         .suggests(new PlayerSuggestionProvider())
                         .executes(context -> showSelect(context.getSource(), PlayerArgumentType.getPlayer(context, "player"))));
    }

    private static int showSelect(ServerCommandSource source, String player) {
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
}

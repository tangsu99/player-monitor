package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;

public class ShowSelectSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
         return CommandManager
                .literal("show")
                .executes((ShowSelectSubCmd::showSelect));
    }

    private static int showSelect(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }
}

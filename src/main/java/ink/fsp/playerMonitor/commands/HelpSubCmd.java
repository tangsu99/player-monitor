package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class HelpSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
        return CommandManager
                .literal("help")
                .executes(HelpSubCmd::exec);
    }
    public static int exec(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.of("help"));
        return 1;
    }
}

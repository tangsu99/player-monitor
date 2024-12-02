package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class ShowSelectSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
         return CommandManager
                .literal("show")
                .executes((ShowSelectSubCmd::showSelect));
    }

    private static int showSelect(CommandContext<ServerCommandSource> ctx) {
        if (ctx.getSource().getPlayer() != null) {
            ctx.getSource().sendMessage(Text.of("未实现"));
        }
        ctx.getSource().sendMessage(Text.of("此命令只能玩家执行"));
        return 1;
    }
}

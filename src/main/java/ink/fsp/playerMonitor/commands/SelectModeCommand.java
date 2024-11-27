package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.utils.PlayerSelectInterface;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class SelectModeCommand {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager
                .literal("selectmode")
                .executes((SelectModeCommand::exec))
        );
    }
    private static int exec(CommandContext<ServerCommandSource> ctx){
        PlayerSelectInterface mode = (PlayerSelectInterface) ctx.getSource().getPlayer();
        if (mode != null) {
            mode.setSelectMode(!mode.isSelectMode());
            if (mode.isSelectMode()) {
                ctx.getSource().getPlayer().sendMessage(Text.of("Selected mode on"));
            }else {
                ctx.getSource().getPlayer().sendMessage(Text.of("Selected mode off"));
            }
        }
        return 1;
    }
}

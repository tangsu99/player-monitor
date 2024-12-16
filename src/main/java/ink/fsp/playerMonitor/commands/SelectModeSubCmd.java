package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.utils.PlayerSelectInterface;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class SelectModeSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
        return CommandManager
                .literal("select")
                .executes(SelectModeSubCmd::exec)
                .then(CommandManager.literal("commit")
                        .then(CommandManager
                                .argument("name", StringArgumentType.string())
                                .executes(context -> commit(context.getSource(), StringArgumentType.getString(context, "name"), null))
                                .then(CommandManager
                                        .argument("comments", StringArgumentType.greedyString())
                                        .executes(context -> commit(context.getSource(), StringArgumentType.getString(context, "name"), StringArgumentType.getString(context, "comments")))
                                )
                        )
                );
    }
    private static int exec(CommandContext<ServerCommandSource> ctx){
        if (ctx.getSource().getPlayer() == null) {
            ctx.getSource().sendMessage(Text.of("此命令只能玩家执行"));
            return 1;
        }
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

    private static int commit(ServerCommandSource source, String name, String comments){
        if (source.getPlayer() == null) {
            source.sendMessage(Text.of("此命令只能玩家执行"));
            return 1;
        }
        PlayerSelectInterface ps = (PlayerSelectInterface) source.getPlayer();
        if (name == null || name.isEmpty()) {
            source.sendMessage(Text.of("<name> field is empty"));
            return 1;
        }
        source.sendMessage(Text.of("name: " + name + "; comments: " + (comments == null ? "" : comments)));
//        if (ps.isSelectMode()) {
//            source.getPlayer().sendMessage(Text.of("Selected mode on"));
//        }else {
//            source.getPlayer().sendMessage(Text.of("Selected mode off"));
//        }
//        ps.setSelectMode(!ps.isSelectMode());
        return 1;
    }
}

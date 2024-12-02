package ink.fsp.playerMonitor.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class CommandsRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ShowTestCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    // 仅在服务器注册命令
                    if (environment.dedicated) {
                        dispatcher.register(CommandManager
                                .literal("monitor")
                                .executes(HelpSubCmd::exec)
                                .then(HelpSubCmd.register())
                                .then(ShowSelectSubCmd.register())
                                .then(SelectModeSubCmd.register())
                        );
                    }
                });
    }
}

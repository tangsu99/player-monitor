package ink.fsp.playerMonitor.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class CommandsRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ShowTestCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    dispatcher.register(CommandManager
                            .literal("monitor")
                            .then(ShowSelectSubCmd.register())
                            .then(SelectModeSubCmd.register())
                    );
                });
    }
}

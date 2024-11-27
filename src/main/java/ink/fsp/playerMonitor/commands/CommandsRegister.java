package ink.fsp.playerMonitor.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandsRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ShowTestCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SelectModeCommand.register(dispatcher));
    }
}

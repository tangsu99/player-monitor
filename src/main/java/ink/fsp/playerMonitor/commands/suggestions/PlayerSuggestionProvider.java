package ink.fsp.playerMonitor.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import ink.fsp.playerMonitor.monitor.MonitorManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class PlayerSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (String suggestion : MonitorManager.players) {
            if (suggestion.startsWith(builder.getRemaining())) {
                builder.suggest(suggestion);
            }
        }
        return builder.buildFuture();
    }
}

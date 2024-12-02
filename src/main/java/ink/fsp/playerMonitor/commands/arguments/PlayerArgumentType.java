package ink.fsp.playerMonitor.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerArgumentType implements ArgumentType<String> {
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        // 解析逻辑，返回一个字符串
        int cursor = reader.getCursor();
        // 假设我们解析一个简单的单词
        String result = reader.readUnquotedString();
        reader.setCursor(cursor); // 重置光标位置，以便可以再次解析
        return result;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String[] suggestions = {"suggestion1", "suggestion2", "suggestion3"};
        for (String suggestion : suggestions) {
            if (suggestion.startsWith(builder.getRemainingLowerCase())) {
                builder.suggest(suggestion);
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return ArgumentType.super.getExamples();
    }
}

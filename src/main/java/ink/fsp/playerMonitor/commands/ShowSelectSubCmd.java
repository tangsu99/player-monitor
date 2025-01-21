package ink.fsp.playerMonitor.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.commands.suggestions.PlayerSuggestionProvider;
import ink.fsp.playerMonitor.commands.suggestions.RegionSuggestionProvider;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import ink.fsp.playerMonitor.database.ResultItem.TrackerItem;
import ink.fsp.playerMonitor.monitor.MonitorManager;
import ink.fsp.playerMonitor.particle.ParticleItem;
import ink.fsp.playerMonitor.particle.ParticleManager;
import ink.fsp.playerMonitor.utils.DimensionTranslation;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowSelectSubCmd {
    private final static Logger LOGGER = PlayerMonitor.LOGGER;
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
         return CommandManager.literal("show")
                 .executes(
                         context -> showSelect(
                                 context.getSource()
                         )
                 )
                 .then(CommandManager.argument("player", StringArgumentType.string())
                         .suggests(new PlayerSuggestionProvider())
                         .then(CommandManager.argument("region", StringArgumentType.string())
                                 .suggests(new RegionSuggestionProvider())
                                 .then(CommandManager.argument("time", IntegerArgumentType.integer())
                                         .executes(
                                                 context -> showSelectByTime(
                                                         context.getSource(),
                                                         StringArgumentType.getString(context, "player"),
                                                         StringArgumentType.getString(context, "region"),
                                                         IntegerArgumentType.getInteger(context, "time")
                                                 )
                                         )
                                 )
                         )
                 );
    }

    private static int showSelect(ServerCommandSource source) {
        if (source.getPlayer() != null) {
            ServerPlayerEntity player = source.getPlayer();
            String world = player.getWorld().getRegistryKey().getValue().toString();
            List<RegionItem> result =  MonitorManager.regions.stream().filter(item -> item.world.equals(world)).toList();
            result.forEach(
                    regionItem -> source.sendMessage(Text
                            .literal(regionItem.regionName)
                            .append(" t[ x: ")
                            .append(String.valueOf(regionItem.start.x))
                            .append(", y: ")
                            .append(String.valueOf(regionItem.start.y))
                            .append(", z: ")
                            .append(String.valueOf(regionItem.start.z))
                            .append(" ]")
                    )
            );
            source.sendMessage(Text.of(DimensionTranslation.translation(world) + " 共 " + result.size() + " 条数据"));
        }
        return 0;
    }

    private static int showSelectByPlayer(ServerCommandSource source, String player) {
        if (source.getPlayer() != null) {
//            source.sendFeedback(() -> Text.of(player), false);
//            ParticleUtils.drawParticleBox(
//                    source.getServer().getOverworld(), source.getEntity().getPos(),
//                    10,
//                    10,
//                    10,
//                    10,
//                    10,
//                    true
//            );
            return 1;
        }
        source.sendMessage(Text.of("此命令只能玩家执行"));
        return 0;
    }

    // 分钟前
    private static int showSelectByTime(ServerCommandSource source, String playerName, String regionName, int time) {
        if (source.getPlayer() != null) {
            SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
            Date currentTime = new Date();
            Date targetTime = new Date(currentTime.getTime() - (time * 1000L * 60L));
            source.sendFeedback(() -> Text.of(playerName + ": " +ft.format(currentTime) + " " + ft.format(targetTime)), false);
            ArrayList<TrackerItem> result = DatabaseManager.selectTrackn(playerName, regionName, currentTime, targetTime);
            if(result != null && !result.isEmpty()) {
                result.forEach(trackerItem -> {
                    if(!trackerItem.dimension.equals(source.getPlayer().getWorld().getRegistryKey().getValue().toString())){
                        return;
                    }
                    ParticleManager.addParticle(new ParticleItem(source.getWorld(), new Vec3d(trackerItem.x, trackerItem.y, trackerItem.z), 20, true));
                });
            }
            source.sendMessage(Text.of("查询到" + result.size() + "条数据"));
            return 1;
        }
        source.sendMessage(Text.of("此命令只能玩家执行"));
        return 0;
    }
}

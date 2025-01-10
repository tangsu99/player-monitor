package ink.fsp.playerMonitor.events;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;

import java.util.Date;

public class PlayerJoinRegionEvent implements PlayerJoinRegionCallback{
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public ActionResult onPlayerJoinRegion(PlayerEntity player, RegionItem regionItem) {
        LOGGER.info("{} 进入了 {}.", player.getGameProfile().getName(), regionItem.regionName);
        // 记录
        player.getServer().getPlayerManager().getPlayerList().forEach(p -> DatabaseManager.insertTrackn(
                p.getGameProfile().getId(),
                regionItem.regionName,
                p.getX(),
                p.getY(),
                p.getZ(),
                p.getWorld().getDimensionEntry().getIdAsString(),
                new Date()
        ));
        return ActionResult.PASS;
    }
}

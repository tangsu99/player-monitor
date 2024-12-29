package ink.fsp.playerMonitor.events;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;

public class PlayerLeaveRegionEvent implements PlayerLeaveRegionCallback{
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    @Override
    public ActionResult onPlayerLeaveRegion(PlayerEntity player, RegionItem regionItem) {
        LOGGER.info("{} 离开了 {}.", player.getGameProfile().getName(), regionItem.regionName);
        return ActionResult.PASS;
    }
}

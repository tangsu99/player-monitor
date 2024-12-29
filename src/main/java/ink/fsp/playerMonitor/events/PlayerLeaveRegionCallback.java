package ink.fsp.playerMonitor.events;

import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerLeaveRegionCallback {
    Event<PlayerLeaveRegionCallback> EVENT = EventFactory.createArrayBacked(PlayerLeaveRegionCallback.class,
            (listeners) -> (player, RegionItem) -> {
                for (PlayerLeaveRegionCallback listener : listeners) {
                    ActionResult result = listener.onPlayerLeaveRegion(player, RegionItem);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onPlayerLeaveRegion(PlayerEntity player, RegionItem regionItem);
}

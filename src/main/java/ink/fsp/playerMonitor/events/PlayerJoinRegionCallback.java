package ink.fsp.playerMonitor.events;

import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerJoinRegionCallback {
    Event<PlayerJoinRegionCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinRegionCallback.class,
            (listeners) -> (player, RegionItem) -> {
                for (PlayerJoinRegionCallback listener : listeners) {
                    ActionResult result = listener.onPlayerJoinRegion(player, RegionItem);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onPlayerJoinRegion(PlayerEntity player, RegionItem regionItem);
}

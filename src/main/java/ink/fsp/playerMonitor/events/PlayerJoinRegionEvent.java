package ink.fsp.playerMonitor.events;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;

public class PlayerJoinRegionEvent implements PlayerJoinRegionCallback{
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    public ActionResult onPlayerJoinRegion(PlayerEntity player, RegionItem regionItem) {
        player.sendMessage(Text.of(player.getGameProfile().getName() + " 进入了 " + regionItem.regionName + "."));
        return ActionResult.PASS;
    }
}

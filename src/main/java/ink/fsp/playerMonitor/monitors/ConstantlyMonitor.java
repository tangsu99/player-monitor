package ink.fsp.playerMonitor.monitors;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class ConstantlyMonitor implements PlayerMoveCallback {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    @Override
    public ActionResult onPlayerUseEntity(PlayerEntity player, World world) {
        LOGGER.info(
                "{}[x: {}, y: {}, z: {}] in {}",
                player.getGameProfile().getName(),
                player.getX(),
                player.getY(),
                player.getZ(),
                world.getDimensionEntry().getIdAsString()
        );
        return ActionResult.PASS;
    }
}

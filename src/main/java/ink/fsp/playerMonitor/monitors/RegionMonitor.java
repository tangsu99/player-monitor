package ink.fsp.playerMonitor.monitors;

import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class RegionMonitor implements PlayerMoveCallback {
    @Override
    public ActionResult onPlayerMove(PlayerEntity player, World world) {
        return ActionResult.PASS;
    }
}

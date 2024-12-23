package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import ink.fsp.playerMonitor.utils.Vec3dUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class RegionMonitor implements PlayerMoveCallback {
    @Override
    public ActionResult onPlayerMove(PlayerEntity player, World world) {
        MonitorManager.regions.forEach(r -> {
            if (Vec3dUtils.isPointInRange(player.getPos(), r.start, r.end)) {
                player.sendMessage(Text.of("在区域内" + (int)player.getPos().x + ", " + (int)player.getPos().y + ", " + (int)player.getPos().z ));
            }
        });
        return ActionResult.PASS;
    }
}

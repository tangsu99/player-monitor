package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.events.PlayerJoinRegionCallback;
import ink.fsp.playerMonitor.events.PlayerLeaveRegionCallback;
import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import ink.fsp.playerMonitor.utils.PlayerFlagInterface;
import ink.fsp.playerMonitor.utils.Vec3dUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class RegionMonitor implements PlayerMoveCallback {
    private int ticks = 0;
    private static final Logger LOGGER = PlayerMonitor.LOGGER;


    @Override
    public ActionResult onPlayerMove(PlayerEntity player, World world) {
        for(var r : MonitorManager.regions) {
            PlayerFlagInterface playerFlagInterface = (PlayerFlagInterface) player;
            // 判断是否进入某区域
            if (Vec3dUtils.isPointInRange(player.getPos(), r.start, r.end) && r.regionName.equals(player.getWorld().getDimensionEntry().getIdAsString())) {
                if (playerFlagInterface.getRegion() == null) {
                    playerFlagInterface.setRegion(r);
                    PlayerJoinRegionCallback.EVENT.invoker().onPlayerJoinRegion(player, r);
//                    player.sendMessage(Text.of(player.getGameProfile().getName() + "进入" + r.regionName + "[" + (int) player.getPos().x + ", " + (int) player.getPos().y + ", " + (int) player.getPos().z + "]"));
                }
                if (ticks++ % 100 == 0) {
                    ticks = 0;
                }
            }else if (playerFlagInterface.getRegion() != null && playerFlagInterface.getRegion() == r) {
                PlayerLeaveRegionCallback.EVENT.invoker().onPlayerLeaveRegion(player, r);
//                player.sendMessage(Text.of(player.getGameProfile().getName() + "离开" + r.regionName + "[" + (int) player.getPos().x + ", " + (int) player.getPos().y + ", " + (int) player.getPos().z + "]"));
                playerFlagInterface.setRegion(null);
            }
        }
        return ActionResult.PASS;
    }
}

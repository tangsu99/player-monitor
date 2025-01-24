package ink.fsp.playerMonitor.monitor;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.DatabaseManager;
import ink.fsp.playerMonitor.events.PlayerJoinRegionCallback;
import ink.fsp.playerMonitor.events.PlayerLeaveRegionCallback;
import ink.fsp.playerMonitor.events.PlayerMoveCallback;
import ink.fsp.playerMonitor.utils.PlayerFlagInterface;
import ink.fsp.playerMonitor.utils.Vec3dUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.Date;

public class RegionMonitor implements PlayerMoveCallback {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    // TODO
    // 记录所有，然后根据选区查
    @Override
    public ActionResult onPlayerMove(PlayerEntity player, World world) {
        if(player.getServer()==null) {
            return ActionResult.PASS;
        }
        if (player.getServer().getTicks() % 10 == 0) {
            for(var r : MonitorManager.regions) {
                // 判断玩家是不是处于记录区同维度
                if(!r.world.equals(player.getWorld().getRegistryKey().getValue().toString())) {
                    continue;
                }
                PlayerFlagInterface playerFlagInterface = (PlayerFlagInterface) player;
                // 判断是否进入某区域
                if (Vec3dUtils.isPointInRange(player.getPos(), r.start, r.end)) {
                    if (playerFlagInterface.getRegion() == null) {
                        playerFlagInterface.setRegion(r);
                        PlayerJoinRegionCallback.EVENT.invoker().onPlayerJoinRegion(player, r);
                    }
                    // 记录
                    player.getServer().getPlayerManager().getPlayerList().forEach(p -> DatabaseManager.insertTrackn(
                            p.getGameProfile().getName(),
                            r.regionName,
                            p.getX(),
                            p.getY(),
                            p.getZ(),
                            p.getWorld().getDimensionEntry().getIdAsString(),
                            new Date()
                    ));
                }else if (playerFlagInterface.getRegion() != null && playerFlagInterface.getRegion() == r) {
                    PlayerLeaveRegionCallback.EVENT.invoker().onPlayerLeaveRegion(player, r);
                    playerFlagInterface.setRegion(null);
                }
            }
        }
        return ActionResult.PASS;
    }
}

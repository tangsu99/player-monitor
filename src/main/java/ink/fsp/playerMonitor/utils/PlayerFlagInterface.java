package ink.fsp.playerMonitor.utils;

import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import net.minecraft.util.math.Vec3d;

public interface PlayerFlagInterface {
    void setSelectPositionStart(Vec3d pos);
    void setSelectPositionEnd(Vec3d pos);
    Vec3d getSelectPositionStart();
    Vec3d getSelectPositionEnd();
    void setFlag();
    boolean getFlag();
    void setSelectMode(boolean selectMode);
    boolean isSelectMode();
    void setRegion(RegionItem regionItem);
    RegionItem getRegion();
}

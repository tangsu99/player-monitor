package ink.fsp.playerMonitor.utils;

import net.minecraft.util.math.Vec3d;

public interface PlayerSelectInterface {
    void setSelectPositionStart(Vec3d pos);
    void setSelectPositionEnd(Vec3d pos);

    Vec3d getSelectPositionStart();
    Vec3d getSelectPositionEnd();
    void setSelectMode(boolean selectMode);
    boolean isSelectMode();
}

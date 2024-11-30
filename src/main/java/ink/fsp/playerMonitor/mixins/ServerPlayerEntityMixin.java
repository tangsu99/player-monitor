package ink.fsp.playerMonitor.mixins;

import ink.fsp.playerMonitor.utils.PlayerSelectInterface;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements PlayerSelectInterface {
    @Unique
    private boolean isSelectMode = false;
    @Unique
    private Vec3d startPos = null;
    @Unique
    private Vec3d endPos = null;
    private boolean flag = false;

    @Override
    public void setSelectPositionStart(Vec3d pos) {
        this.startPos = pos;
    }

    @Override
    public void setFlag() {
        this.flag = true;
    }

    @Override
    public boolean getFlag() {
        return this.flag;
    }

    @Override
    public Vec3d getSelectPositionStart() {
        return this.startPos;
    }

    @Override
    public void setSelectPositionEnd(Vec3d pos) {
        this.endPos = pos;
    }

    @Override
    public Vec3d getSelectPositionEnd() {
        return this.endPos;
    }

    @Override
    public void setSelectMode(boolean selectMode) {
        this.isSelectMode = selectMode;
    }

    @Override
    public boolean isSelectMode() {
        return this.isSelectMode;
    }
}

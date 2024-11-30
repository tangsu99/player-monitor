package ink.fsp.playerMonitor.monitors;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.particle.ParticleItem;
import ink.fsp.playerMonitor.particle.ParticleManager;
import ink.fsp.playerMonitor.particle.ParticleUtils;
import ink.fsp.playerMonitor.utils.PlayerSelectInterface;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class PlayerHoeEvent {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;

    public void register() {
        AttackBlockCallback.EVENT.register(this::onLeftClickBlock);
        UseBlockCallback.EVENT.register(this::onRightClickBlock);
//        UseItemCallback.EVENT.register(this::onRightClickItem);
    }

    private ActionResult onLeftClickBlock(PlayerEntity player, World world, Hand hand, BlockPos blockPos, Direction direction) {
        if (!world.isClient()) {
            PlayerSelectInterface playerSelectInterface = (PlayerSelectInterface) player;
            if (playerSelectInterface.isSelectMode() && isSelectTool(player)) {
                player.sendMessage(Text.of("select start: " + blockPos.toCenterPos().toString()));
                playerSelectInterface.setSelectPositionStart(blockPos.toCenterPos());
                ParticleManager.addParticle(new ParticleItem(player.getServer().getWorld(world.getRegistryKey()), blockPos.toCenterPos(), 10, new DustParticleEffect(DustParticleEffect.RED, 0.5F), true));
                if (playerSelectInterface.getSelectPositionStart() != null && playerSelectInterface.getSelectPositionEnd() != null) {
                    ParticleUtils.drawParticleBox(
                            player.getServer().getWorld(world.getRegistryKey()),
                            playerSelectInterface.getSelectPositionStart(),
                            playerSelectInterface.getSelectPositionEnd(),
                            10,
                            10,
                            true
                    );
                }
            }
            return ActionResult.PASS;
        }
        return ActionResult.PASS;
    }


    private ActionResult onRightClickBlock(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
        if (!world.isClient()) {
            PlayerSelectInterface playerSelectInterface = (PlayerSelectInterface) player;
            if (playerSelectInterface.isSelectMode() && isSelectTool(player)) {
                player.sendMessage(Text.of("select end: " + blockHitResult.getPos().toString()));
                playerSelectInterface.setSelectPositionEnd(blockHitResult.getPos());
                ParticleManager.addParticle(new ParticleItem(player.getServer().getWorld(world.getRegistryKey()), blockHitResult.getPos(), 10, new DustParticleEffect(DustParticleEffect.RED, 0.5F), true));
                if (playerSelectInterface.getSelectPositionStart() != null && playerSelectInterface.getSelectPositionEnd() != null) {
                    ParticleUtils.drawParticleBox(
                            player.getServer().getWorld(world.getRegistryKey()),
                            playerSelectInterface.getSelectPositionStart(),
                            playerSelectInterface.getSelectPositionEnd(),
                            10,
                            10,
                            true
                    );
                }
            }
            return ActionResult.PASS;
        }
        LOGGER.info("is client");
        return ActionResult.PASS;
    }

//    private TypedActionResult<ItemStack> onRightClickItem(PlayerEntity player, World world, Hand hand) {
//        if (!world.isClient()) {
//            PlayerSelectInterface playerSelectInterface = (PlayerSelectInterface) player;
//            if (playerSelectInterface.isSelectMode()) {
//                player.sendMessage(Text.of("select mode true, is right, form use item"));
//                player.sendMessage(Text.of("select end: " + blockHitResult.getPos().toString()));
//                playerSelectInterface.setSelectPositionEnd(blockHitResult.getPos());
//            }
//            return TypedActionResult.pass(null);
//        }
//        LOGGER.info("is client");
//        return TypedActionResult.pass(null);
//    }

    private static boolean isSelectTool(PlayerEntity player) {
        return player.getMainHandStack().getItem() == Items.WOODEN_HOE;
    }
}

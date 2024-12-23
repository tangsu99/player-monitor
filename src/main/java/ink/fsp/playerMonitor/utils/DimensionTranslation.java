package ink.fsp.playerMonitor.utils;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public class DimensionTranslation {
    public static String translation(String id) {
        switch (id) {
            case "minecraft:overworld": return "主世界";
            case "minecraft:the_nether": return "下界";
            case "minecraft:the_end": return "末地";
            default: return id;
        }
    }
    public static String translation(RegistryKey<World> key) {
        return translation(key.getValue().toString());
    }
}

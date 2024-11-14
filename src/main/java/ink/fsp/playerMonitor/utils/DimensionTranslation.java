package ink.fsp.playerMonitor.utils;

public class DimensionTranslation {
    public static String translation(String id) {
        switch (id) {
            case "minecraft:overworld": return "主世界";
            case "minecraft:the_nether": return "下界";
            case "minecraft:the_end": return "末地";
            default: return id;
        }
    }
}

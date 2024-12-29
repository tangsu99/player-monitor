package ink.fsp.playerMonitor.events;


public class EventsRegister {
    public static void register() {
        PlayerJoinRegionCallback.EVENT.register(new PlayerJoinRegionEvent());
        PlayerLeaveRegionCallback.EVENT.register(new PlayerLeaveRegionEvent());
    }
}

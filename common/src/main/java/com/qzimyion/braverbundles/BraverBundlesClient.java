package com.qzimyion.braverbundles;

import com.qzimyion.braverbundles.networking.ModNetworkHandler;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;

public class BraverBundlesClient {

    public static void init(){
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ModNetworkHandler.sendToClient(serverPlayer);
            }
        });
        ModNetworkHandler.register();
    }
}

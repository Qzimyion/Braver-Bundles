package com.qzimyion.qzibundletweaks;

import com.qzimyion.qzibundletweaks.events.EntityEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

public class FabricQzisBundleTweaks implements ModInitializer {
    
    @Override
    public void onInitialize() {

        UseEntityCallback.EVENT.register(EntityEvents::bundleStoringItemEntitiesEvent);
        CommonClass.init();
    }
}

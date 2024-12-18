package com.qzimyion.qzibundletweaks;

import com.qzimyion.qzibundletweaks.events.EntityEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = QzisBundleTweaksConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoEvents {

    @SubscribeEvent
    public static void ItemEntityEvents(PlayerInteractEvent.EntityInteract event){
        if (event.isCanceled()) return;
        InteractionResult NeoForgeRightClickEvent = EntityEvents.bundleStoringItemEntitiesEvent
                (event.getEntity(), event.getLevel(), event.getHand(), event.getEntity(), new EntityHitResult(event.getEntity()));
        event.setCanceled(true);
        event.setCancellationResult(NeoForgeRightClickEvent);
    }
}

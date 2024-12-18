package com.qzimyion.qzibundletweaks;

import com.qzimyion.qzibundletweaks.events.EntityEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QzisBundleTweaksConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void ItemEntityEvents(PlayerInteractEvent.EntityInteract event){
        if (event.isCanceled()) return;
        InteractionResult ForgeRightClickEvent = EntityEvents.bundleStoringItemEntitiesEvent
                (event.getEntity(), event.getLevel(), event.getHand(), event.getEntity(), new EntityHitResult(event.getEntity()));
        event.setCanceled(true);
        event.setCancellationResult(ForgeRightClickEvent);
    }
}

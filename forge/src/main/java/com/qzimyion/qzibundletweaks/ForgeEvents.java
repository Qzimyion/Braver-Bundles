package com.qzimyion.qzibundletweaks;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QzisBundleTweaksConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

//    @SubscribeEvent
//    public static void ItemEntityEvents(PlayerInteractEvent.EntityInteract event){
//        if (event.isCanceled()) return;
//        BlockPos pos = event.getPos();
//        BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);
//        void ForgeRightClickEvent = EntityEvents.bundleStoringItemEntitiesEvent
//                (event.getLevel(), event.getEntity(), event.getPos(), event.getLevel().getBlockState(event.getPos()), blockEntity);
//        event.setCanceled(true);
//        event.setCancellationResult(ForgeRightClickEvent);
//    }
}

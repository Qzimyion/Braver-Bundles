package com.qzimyion.qzibundletweaks.events;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;


public class EntityEvents {

    public static InteractionResult bundleStoringItemEntitiesEvent(Player player, Level level, InteractionHand hand, Entity entity, EntityHitResult entityHitResult){
        ItemStack heldItem = player.getItemInHand(hand);
        if (entity instanceof ItemEntity item && heldItem.is(ItemTags.BUNDLES)){
            BundleContents bundleContents = item.getItem().get(DataComponents.BUNDLE_CONTENTS);
            ItemStack itemStackInEntity = item.getItem();
            assert bundleContents != null;
            BundleContents.Mutable mutableContents = new BundleContents.Mutable(bundleContents);
            if (!itemStackInEntity.isEmpty()){
                if (mutableContents.tryInsert(itemStackInEntity) > 0) {
                    itemStackInEntity.shrink(1);
                    heldItem.set(DataComponents.BUNDLE_CONTENTS, mutableContents.toImmutable());
                    player.playSound(SoundEvents.BUNDLE_INSERT, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                } else {
                    player.playSound(SoundEvents.BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.PASS;
    }
}

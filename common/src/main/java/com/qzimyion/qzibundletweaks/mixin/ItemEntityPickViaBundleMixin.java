package com.qzimyion.qzibundletweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Block.class)
public abstract class ItemEntityPickViaBundleMixin {
    @ModifyReturnValue(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            at = @At("TAIL"))
    private static List<ItemStack> putDropsInBundle(List<ItemStack> original, BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool) {
        if (!(entity instanceof Player player)) return original;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!(heldItem.getItem() instanceof BundleItem)) continue;
            BundleContents bundleContents = heldItem.get(DataComponents.BUNDLE_CONTENTS);
            if (bundleContents == null) continue;
            BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
            original.forEach(mutable::tryInsert);
            heldItem.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_INSERT, player.getSoundSource(), 1.0F, 1.0F);
        }
        return original;
    }


}

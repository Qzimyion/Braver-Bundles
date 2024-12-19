package com.qzimyion.qzibundletweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerGameMode.class)
public class ItemEntityPickViaBundleMixin {

    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    @Inject(at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/level/block/Block;playerDestroy(Lnet/minecraft/world/level/Level;" +
                    "Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;" +
                    "Lnet/minecraft/world/level/block/state/BlockState;" +
                    "Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/item/ItemStack;)V"), method = "destroyBlock")
    private void onBlockBroken(BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        BlockState state = level.getBlockState(pos).getBlock().defaultBlockState();
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack heldItem = player.getItemInHand(hand);
            Level level = player.level();

            if (heldItem.getItem() instanceof BundleItem) {
                BundleContents bundleContents = heldItem.get(DataComponents.BUNDLE_CONTENTS);
                if (bundleContents == null) {
                    return;
                }

                BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
                List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, state.getShape(level, pos).bounds().move(pos));
                if (itemEntities.isEmpty()) {
                    return;
                }
                for (ItemEntity itemEntity : itemEntities) {
                    ItemStack droppedItem = itemEntity.getItem();
                    int insertedCount = mutable.tryInsert(droppedItem);
                    if (insertedCount > 0) {
                        droppedItem.shrink(insertedCount);
                        if (droppedItem.isEmpty()) {
                            itemEntity.discard();
                        }
                    }
                }
                heldItem.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_INSERT, player.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }
}

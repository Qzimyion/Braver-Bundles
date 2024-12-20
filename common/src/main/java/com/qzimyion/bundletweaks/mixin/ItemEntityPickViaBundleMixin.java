package com.qzimyion.bundletweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
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

@Mixin(Block.class)
public class ItemEntityPickViaBundleMixin {

	// Just a little cleanup here.
	// Don't forget to check where sounds are played from! I was able to just use bundleItem.playInsertSound instead or your re-written sound method.

	@ModifyReturnValue(
		method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
		at = @At("TAIL")
	)
	private static List<ItemStack> putDropsInBundle(
		List<ItemStack> original, BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool
	) {
		if (entity instanceof Player player) {
			for (InteractionHand hand : InteractionHand.values()) {
				ItemStack itemInHand = player.getItemInHand(hand);
				if (itemInHand.getItem() instanceof BundleItem bundleItem) {
					BundleContents bundleContents = itemInHand.get(DataComponents.BUNDLE_CONTENTS);
					if (bundleContents == null) continue;
					BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
					original.forEach(mutable::tryInsert);
					itemInHand.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
					bundleItem.playInsertSound(entity);
				}
			}
		}
		return original;
	}


}

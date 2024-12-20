package com.qzimyion.bundletweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.List;
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
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockDropsPickupViaBundleMixin {

	@ModifyReturnValue(
		method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
		at = @At("TAIL")
	)
	private static List<ItemStack> putDropsInBundle(
		List<ItemStack> original, BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool
	) {
		if (entity instanceof Player player && !original.isEmpty()) {
			BlockPos basePos;
			// Handle doors
			if (state.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
				basePos = pos.below();
				BlockState baseState = level.getBlockState(basePos);
				List<ItemStack> baseDrops = Block.getDrops(baseState, level, basePos, null, player, tool);
				original.addAll(baseDrops);
			}
			for (InteractionHand hand : InteractionHand.values()) {
				ItemStack itemInHand = player.getItemInHand(hand);
				if (itemInHand.getItem() instanceof BundleItem) {
					BundleContents bundleContents = itemInHand.get(DataComponents.BUNDLE_CONTENTS);
					if (bundleContents != null) {
						BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
						for (ItemStack itemStack : original) {
							if (mutable.tryInsert(itemStack) != 0) {
								level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_INSERT,
									player.getSoundSource(), 1F, 0.8F + level.getRandom().nextFloat() * 0.4F);
							}
						}
						itemInHand.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
					}
				}
			}
		}
		return original;
	}


}

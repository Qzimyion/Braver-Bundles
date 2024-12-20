package com.qzimyion.bundletweaks.mixin;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BundleItem.class)
public class BundleRandomBlockPlacementMixin extends Item {

	public BundleRandomBlockPlacementMixin(Properties properties) {
		super(properties);
	}

	// I highly encourage you to look at the differences between my version of this mixin and your original!

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		if (context.getLevel().isClientSide) return InteractionResult.CONSUME;

		Player player = context.getPlayer();
		if (player != null && player.isShiftKeyDown()) {
			ItemStack itemInHand = context.getItemInHand();
			BundleContents bundleContents = itemInHand.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
			RandomSource random = player.getRandom();

			// This piece here makes sure a BlockItem always get selected.
			// Before, a non-BlockItem could be selected from the Bundle and make the task of placing Blocks frustrating.
			List<ItemStack> blockItems = bundleContents.itemCopyStream()
				.filter(itemStack -> itemStack.getItem() instanceof BlockItem && !player.getCooldowns().isOnCooldown(itemStack) && !itemStack.isEmpty())
				.toList();

			if (blockItems.isEmpty()) return super.useOn(context);

			ItemStack selectedItem = Util.getRandom(blockItems, random);
			if (selectedItem.getItem() instanceof BlockItem blockItem) {
				BlockPlaceContext placeContext = new BlockPlaceContext(player, context.getHand(), itemInHand, context.getHitResult());
				InteractionHand swingingArm = player.swingingArm;
				// This doesn't preserve precise placement behavior of some blocks :(
				InteractionResult result = blockItem.useOn(placeContext);
				if (result.consumesAction()) {
					if (!context.getPlayer().isCreative()) {
						selectedItem.shrink(1);
						BundleContents newBundleContents = new BundleContents(blockItems);
						itemInHand.set(DataComponents.BUNDLE_CONTENTS, newBundleContents);
					}
					BlockState placedBlockState = blockItem.getBlock().defaultBlockState();
					player.swing(swingingArm);
					SoundType soundType = placedBlockState.getSoundType();
					context.getLevel().playSound(
						null,
						context.getClickedPos(),
						soundType.getPlaceSound(), SoundSource.BLOCKS,
						(soundType.getVolume() + 1F) / 2F,
						soundType.getPitch() * 0.8F
					);
					player.awardStat(Stats.ITEM_USED.get(this));
					player.awardStat(Stats.ITEM_USED.get(blockItem));
				}
				return result;
			}
		}
		return super.useOn(context);
	}

}

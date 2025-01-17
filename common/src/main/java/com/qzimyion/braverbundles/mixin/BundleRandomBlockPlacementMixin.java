package com.qzimyion.braverbundles.mixin;

import java.util.ArrayList;
import java.util.List;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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

			IntArrayList indexes = getBlockItemIndexes(bundleContents, player);
			if (indexes.isEmpty()) return super.useOn(context);

			int index = Util.getRandom(indexes, random);
			ItemStack selectedItem = bundleContents.getItemUnsafe(index);

			if (selectedItem.getItem() instanceof BlockItem blockItem) {
				BlockPlaceContext placeContext = new BlockPlaceContext(player, context.getHand(), selectedItem, context.getHitResult());
				InteractionHand swingingArm = player.swingingArm;
				// This doesn't preserve precise placement behavior of some blocks :(
				InteractionResult result = blockItem.useOn(placeContext);
				if (result.consumesAction()) {
					updateBundle(itemInHand, index);
					BlockState placedBlockState = blockItem.getBlock().defaultBlockState();
					player.swing(swingingArm);
					SoundType soundType = placedBlockState.getSoundType();
					Level level = context.getLevel();
					BlockPos clickedPos = context.getClickedPos();

					level.playSound(
						null,
						clickedPos,
						soundType.getPlaceSound(), SoundSource.BLOCKS,
						(soundType.getVolume() + 1F) / 2F,
						soundType.getPitch() * 0.8F
					);
					level.playSound(
						null,
						clickedPos,
						SoundEvents.BUNDLE_REMOVE_ONE,
						player.getSoundSource(),
						1F,
						0.8F + level.getRandom().nextFloat() * 0.4F
					);
					player.awardStat(Stats.ITEM_USED.get(blockItem));
				}
				return result;
			}
		}
		return super.useOn(context);
	}

	@Unique
	private static @NotNull IntArrayList getBlockItemIndexes(@NotNull BundleContents bundleContents, Player player) {
		IntArrayList indexes = new IntArrayList();
		for (int i = 0; i < bundleContents.size(); i++) {
			ItemStack itemStack = bundleContents.getItemUnsafe(i);
			if (!itemStack.isEmpty() && itemStack.getItem() instanceof BlockItem && !player.getCooldowns().isOnCooldown(itemStack)) {
				indexes.add(i);
			}
		}
		return indexes;
	}

	@Unique
	private static void updateBundle(@NotNull ItemStack bundleItemStack, int index) {
		BundleContents bundleContents = bundleItemStack.get(DataComponents.BUNDLE_CONTENTS);
		if (bundleContents != null && !bundleContents.isEmpty() && index >= 0 && index < bundleContents.size()) {
			List<ItemStack> stacks = new ArrayList<>(bundleContents.itemCopyStream().toList());
			ItemStack stackAtIndex = stacks.get(index).copy();
			if (stackAtIndex.isEmpty()) {
				stacks.remove(index);
			} else {
				stacks.set(index, stackAtIndex);
			}
			BundleContents updatedContents = new BundleContents(stacks);
			bundleItemStack.set(DataComponents.BUNDLE_CONTENTS, updatedContents);
		}
	}
}

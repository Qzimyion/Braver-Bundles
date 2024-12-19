package com.qzimyion.qzibundletweaks.mixin;

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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(BundleItem.class)
public class BundleRandomBlockPlacementMixin extends Item {

    public BundleRandomBlockPlacementMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack bundle = Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand());
        BundleContents bundleContents = bundle.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        if (context.getLevel().isClientSide) {
            return InteractionResult.CONSUME;
        }
        RandomSource random = context.getLevel().getRandom();
        List<Integer> availableIndexes = bundleIndices(bundleContents);
        int selectedIndex = availableIndexes.get(random.nextInt(availableIndexes.size()));
        ItemStack selectedStack = bundleContents.getItemUnsafe(selectedIndex).copy();
        if (!(selectedStack.getItem() instanceof BlockItem blockItem)) {
            return InteractionResult.FAIL;
        }
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        if (availableIndexes.isEmpty()) {
            return super.useOn(context);
        }
        InteractionResult result = null;
        assert player != null;
        InteractionHand swingingArm = player.swingingArm;
        if (player.isShiftKeyDown()) {
            result = blockItem.useOn(placeContext);
            if (result.consumesAction()) {
                if (!context.getPlayer().isCreative()) {
                    removeItem(context.getPlayer(), bundle, selectedIndex);
                }
                BlockState placedBlockState = blockItem.getBlock().defaultBlockState();
                player.swing(swingingArm);
                SoundType soundType = placedBlockState.getSoundType();
                context.getLevel().playSound(null, context.getClickedPos(),
                        soundType.getPlaceSound(), SoundSource.BLOCKS,
                        (soundType.getVolume() + 1.0F) / 2.0F,
                        soundType.getPitch() * 0.8F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        assert result != null;
        return result;
    }

    @Unique
    private List<Integer> bundleIndices(BundleContents bundleContents) {
        List<Integer> availableIndexes = new ArrayList<>();
        for (int i = 0; i < bundleContents.size(); i++) {
            if (bundleContents.getItemUnsafe(i).getItem() instanceof BlockItem) {
                availableIndexes.add(i);
            }
        }
        return availableIndexes;
    }

    @Unique
    private void removeItem(Player player, ItemStack bundleItemStack, int index) {
        BundleContents bundleContents = bundleItemStack.get(DataComponents.BUNDLE_CONTENTS);
        if (bundleContents != null && !bundleContents.isEmpty() && index >= 0 && index < bundleContents.size()) {
            List<ItemStack> stacks = new ArrayList<>(bundleContents.itemCopyStream().toList());
            ItemStack itemStack = stacks.get(index).copy();
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                stacks.remove(index);
            } else {
                stacks.set(index, itemStack);
            }
            BundleContents updatedContents = new BundleContents(stacks);
            bundleItemStack.set(DataComponents.BUNDLE_CONTENTS, updatedContents);
        }
    }
}

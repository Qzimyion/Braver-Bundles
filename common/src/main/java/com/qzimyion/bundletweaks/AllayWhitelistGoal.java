package com.qzimyion.bundletweaks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

import java.util.List;

public class AllayWhitelistGoal extends Goal {
    private final Allay allay;
    private final Level level;

    public AllayWhitelistGoal(Allay allay) {
        this.allay = allay;
        this.level = allay.level();
    }

    @Override
    public boolean canUse() {
        return false;
    }

//    @Override
//    public void tick() {
//        ItemStack bundle = allay.getMainHandItem();
//        if (!(bundle.getItem() instanceof BundleItem)) return;
//
//        List<Item> whitelist = getWhitelistFromBundle(bundle);
//
//        List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, allay.getBoundingBox().inflate(8.0));
//
//        for (ItemEntity itemEntity : nearbyItems) {
//            ItemStack itemStack = itemEntity.getItem();
//            Item item = itemStack.getItem();
//
//            if (whitelist.isEmpty() || whitelist.contains(item)) {
//                allay.getNavigation().moveTo(itemEntity, 1.0);
//                if (allay.distanceTo(itemEntity) < 2.0) {
//                    pickupItem(itemEntity, bundle);
//                }
//                break;
//            }
//        }
//    }
//
//    private List<Item> getWhitelistFromBundle(ItemStack bundle) {
//        BundleContents bundleContents = bundle.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
//
//        if (bundleContents.isEmpty()) {
//            return List.of(); // Return an empty whitelist if the bundle is empty
//        }
//        return bundleContents.itemsCopy().stream().map(ItemStack::getItem).toList();
//    }
//
//    private void pickupItem(ItemEntity itemEntity, ItemStack bundle) {
//        ItemStack itemStack = itemEntity.getItem();
//        int initialCount = itemStack.getCount();
//
//        int added = addToBundle(bundle, itemStack);
//
//        if (added >= initialCount) {
//            itemEntity.discard();
//        } else {
//            itemStack.setCount(initialCount - added);
//            itemEntity.setItem(itemStack);
//        }
//    }
//
//    private int addToBundle(ItemStack bundle, ItemStack itemStack) {
//        int maxWeight = 64;
//        int currentWeight = (int) (BundleItem.getFullnessDisplay(bundle) * maxWeight);
//        int itemWeight = itemStack.getCount();
//
//        int spaceLeft = maxWeight - currentWeight;
//        int toAdd = Math.min(spaceLeft, itemWeight);
//
//        if (toAdd > 0) {
//            BundleItem.add(bundle, itemStack.split(toAdd));
//        }
//
//        return toAdd;
//    }
}

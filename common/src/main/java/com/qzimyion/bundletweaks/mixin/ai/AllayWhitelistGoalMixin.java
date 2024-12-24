package com.qzimyion.bundletweaks.mixin.ai;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Allay.class)
public class AllayWhitelistGoalMixin extends PathfinderMob {

	protected AllayWhitelistGoalMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	@ModifyExpressionValue(method = "wantsToPickUp", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/allay/Allay;allayConsidersItemEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
	private boolean allayConsidersItemsEqual(boolean original, @Local(ordinal = 0, argsOnly = true) ItemStack itemStack){
		ItemStack heldStack = getItemInHand(InteractionHand.MAIN_HAND);
		if (!heldStack.is(Items.BUNDLE)) return original;
		if (original) {
			BundleContents bundleContents = heldStack.get(DataComponents.BUNDLE_CONTENTS);
			if (bundleContents == null || bundleContents.isEmpty()) {
				return true;
			}
		}
		return isItemInBundle(heldStack, itemStack);
	}


	private boolean isItemInBundle(ItemStack bundle, ItemStack itemStack) {
		BundleContents bundleContents = bundle.get(DataComponents.BUNDLE_CONTENTS);
		if (bundleContents != null && !bundleContents.isEmpty()) {
			for (ItemStack bundledItem : bundleContents.items()) {
				if (ItemStack.isSameItem(itemStack, bundledItem)) return true;
			}
		}
		return false;
	}
}

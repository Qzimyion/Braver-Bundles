package com.qzimyion.bundletweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import static net.minecraft.world.item.BundleItem.playInsertSound;

@Mixin(BundleItem.class)
public abstract class ItemEntityScoopingViaBundleMixin {

	@Shadow
	private static void playInsertFailSound(Entity entity) {
	}

	@WrapOperation(method = "dropContent(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BundleItem;dropContent(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z"))
	boolean dropContent(BundleItem instance, ItemStack itemStack, Player player, Operation<Boolean> original){
		boolean collect = qzimyions_Bundle_Tweaks$scoopUpItem(itemStack, player);
		if (!collect) {
			return original.call(instance, itemStack, player);
		}
		return false;
	}

	@Unique
	private boolean qzimyions_Bundle_Tweaks$scoopUpItem(ItemStack itemStack, Player player) {
		boolean success = false;
		ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
		HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(player, (entity -> true), player.blockInteractionRange());
		if (hitResult instanceof EntityHitResult entityHitResult) {
			if (entityHitResult.getEntity() instanceof ItemEntity itemEntity) {
				if (stack.is(ItemTags.BUNDLES)){
					itemEntity.getBoundingBox().inflate(10);
					success = true;
					if (qzimyions_Bundle_Tweaks$storeInBundle(itemStack, itemEntity)) {
						playInsertSound(player);
					} else {
						playInsertFailSound(player);
					}
				}
			}
		}
		return success;
	}

	@Unique
	private boolean qzimyions_Bundle_Tweaks$storeInBundle(ItemStack bundleStack, ItemEntity itemEntity) {
		BundleContents bundleContents = bundleStack.get(DataComponents.BUNDLE_CONTENTS);
		if (bundleContents == null) return false;
		int space = bundleContents.weight().getDenominator() - bundleContents.weight().getNumerator();
		BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
		int inserted = 0;
		if (space >= 1) {
			ItemStack stack = itemEntity.getItem().copy();
			inserted = mutable.tryInsert(stack);
			if (inserted != 0) {
				itemEntity.setItem(stack);
			}
		}
		bundleStack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
		return inserted != 0;
	}
}

package com.qzimyion.bundletweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class BundleDecrementFixMixin {

	// Please don't use @Redirect!! It's quite harmful and easily ruins mod compatibility.
	// Use @WrapOperation instead!
	// It gives you the method's parameters and lets you either modify what the original method uses, lets you call another method, etc.
	@WrapOperation(
		method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"
		)
	)
	private void cancelBundleDecrement(ItemStack itemStack, int amount, LivingEntity entity, Operation<Void> original) {
		// Your original implementation called shrink if it's a Bundle, with an amount of 0... accomplishing nothing.
		// It also called shrink with 1 if it wasn't! This breaks Creative Mode blocks not shrinking.
		// I changed this to call the original method instead, or skip if it isn't a Bundle. This way, it works as intended.
		if (!(itemStack.getItem() instanceof BundleItem)) {
			original.call(itemStack, amount, entity);
		}
	}
}

package com.qzimyion.braverbundles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public class BundleDecrementFixMixin {

	@WrapOperation(
		method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"
		)
	)
	private void cancelBundleDecrement(ItemStack itemStack, int amount, LivingEntity entity, Operation<Void> original) {
		if (!(itemStack.getItem() instanceof BundleItem)) {
			original.call(itemStack, amount, entity);
		}
	}
}

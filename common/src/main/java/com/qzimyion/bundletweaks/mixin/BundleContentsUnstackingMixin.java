package com.qzimyion.bundletweaks.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.class)
public class BundleContentsUnstackingMixin {

	@Inject(method = "getWeight", at = @At("TAIL"), cancellable = true)
	private static void getWeight(ItemStack itemStack, CallbackInfoReturnable<Fraction> info) {
		if (itemStack.getMaxStackSize() == 1) {
			info.setReturnValue(Fraction.ONE_QUARTER);
		}
	}
}

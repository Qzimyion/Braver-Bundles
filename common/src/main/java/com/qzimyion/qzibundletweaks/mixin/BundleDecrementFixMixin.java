package com.qzimyion.qzibundletweaks.mixin;

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

    @Redirect(method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    private void cancelBundleDecrement(ItemStack itemStack, int amount, LivingEntity entity) {
        Item item = itemStack.getItem();
        if (item instanceof BundleItem) {
            itemStack.shrink(0);
        } else {
            itemStack.shrink(amount);
        }
    }
}

package com.qzimyion.braverbundles.common;

import com.qzimyion.braverbundles.config.CommonModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;

public class BundleDispenserBehavior implements DispenseItemBehavior {

	@Override
	public @NotNull ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
		if (!CommonModConfig.DISPENSER_FUNC){
			return null;
		}
		OptionalDispenseItemBehavior bundleDispenseBehavior = new OptionalDispenseItemBehavior() {};
		BundleContents bundleContents = itemStack.get(DataComponents.BUNDLE_CONTENTS);
		Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
		BlockPos frontPos = blockSource.pos().relative(direction);
		List<ItemEntity> itemEntityList = blockSource.level().getEntitiesOfClass(
			ItemEntity.class,
			new AABB(frontPos),
			livingEntity -> livingEntity instanceof ItemEntity
		);
		if (itemEntityList.isEmpty()) {
			if (bundleContents != null && !bundleContents.isEmpty()) {
				Optional<ItemStack> optional = removeItemFromBundle(itemStack, bundleContents);
				if (optional.isPresent()) {
					DefaultDispenseItemBehavior.spawnItem(blockSource.level(), optional.get(), 6, direction, DispenserBlock.getDispensePosition(blockSource));
					this.playSound(blockSource);
					// play bundle sound
					playAnimation(blockSource, direction);
					return itemStack;
				}
			}
		}
		else if (bundleContents != null && CommonModConfig.DISPENSER_ITEM_ENTITY_SCOOPING_IF_BUNDLE_INSIDE) {
			int space = bundleContents.weight().getDenominator() - bundleContents.weight().getNumerator();
			BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
			int inserted = 0;
			if (space >= 1) for (ItemEntity itemEntity : itemEntityList) {
				ItemStack stack = itemEntity.getItem().copy();
				inserted = mutable.tryInsert(stack);
				if (inserted != 0) {
					itemEntity.setItem(stack);
					break;
				}
			}
			itemStack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
			bundleDispenseBehavior.setSuccess(inserted != 0);
			return itemStack;
		}
		bundleDispenseBehavior.setSuccess(false);
		return itemStack;
	}

	private static Optional<ItemStack> removeItemFromBundle(ItemStack bundle, BundleContents bundleContents) {
		BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
		ItemStack removedStack = mutable.removeOne();
		if (removedStack != null) {
			bundle.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
			return Optional.of(removedStack);
		} else {
			return Optional.empty();
		}
	}

	protected void playSound(BlockSource blockSource) {
		playDefaultSound(blockSource);
	}

	protected void playAnimation(BlockSource blockSource, Direction direction) {
		playDefaultAnimation(blockSource, direction);
	}

	private static void playDefaultSound(BlockSource blockSource) {
		blockSource.level().levelEvent(1000, blockSource.pos(), 0);
	}

	private static void playDefaultAnimation(BlockSource blockSource, Direction direction) {
		blockSource.level().levelEvent(2000, blockSource.pos(), direction.get3DDataValue());
	}
}

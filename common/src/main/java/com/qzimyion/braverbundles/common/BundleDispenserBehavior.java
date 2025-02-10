package com.qzimyion.braverbundles.common;

import com.qzimyion.braverbundles.CommonModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
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

	protected ItemStack execute(BlockSource blockSource, ItemStack item) {
		Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
		Position position = DispenserBlock.getDispensePosition(blockSource);
		ItemStack itemStack = item.split(1);
		DefaultDispenseItemBehavior.spawnItem(blockSource.level(), itemStack, 6, direction, position);
		return item;
	}

	@Override
	public @NotNull ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
		Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
		if (!CommonModConfig.DISPENSER_FUNC){
			ItemStack itemStack2 = this.execute(blockSource, itemStack);
			this.playSound(blockSource);
			this.playAnimation(blockSource, blockSource.state().getValue(DispenserBlock.FACING));
			return itemStack2;
		}
		OptionalDispenseItemBehavior bundleDispenseBehavior = new OptionalDispenseItemBehavior() {};
		BundleContents bundleContents = itemStack.get(DataComponents.BUNDLE_CONTENTS);
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
					this.playAnimation(blockSource, direction);
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

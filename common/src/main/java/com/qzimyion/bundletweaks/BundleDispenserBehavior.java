package com.qzimyion.bundletweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class BundleDispenserBehavior implements DispenseItemBehavior {

	@Override
	public @NotNull ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
		BundleContents bundleContents = itemStack.get(DataComponents.BUNDLE_CONTENTS);
		Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
		BlockPos frontPos = blockSource.pos().relative(direction);
		List<ItemEntity> itemEntityList = blockSource.level().getEntitiesOfClass(
			ItemEntity.class,
			new AABB(frontPos),
			livingEntity -> livingEntity instanceof ItemEntity
		);
		return null;
	}
}

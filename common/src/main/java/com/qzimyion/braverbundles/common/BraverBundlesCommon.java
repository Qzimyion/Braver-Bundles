package com.qzimyion.braverbundles.common;

import com.qzimyion.braverbundles.config.CommonModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

public class BraverBundlesCommon {

	public static void init(){
		AutoConfig.register(CommonModConfig.class, JanksonConfigSerializer::new);
		DispenserBlock.registerBehavior(Items.BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.WHITE_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.ORANGE_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.CYAN_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.BLUE_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.BROWN_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.BLACK_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.GRAY_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.GREEN_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.LIGHT_BLUE_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.LIGHT_GRAY_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.LIME_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.MAGENTA_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.PINK_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.PURPLE_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.RED_BUNDLE, new BundleDispenserBehavior());
		DispenserBlock.registerBehavior(Items.YELLOW_BUNDLE, new BundleDispenserBehavior());
	}
}

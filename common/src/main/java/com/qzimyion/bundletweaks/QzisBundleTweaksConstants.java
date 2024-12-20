package com.qzimyion.bundletweaks;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QzisBundleTweaksConstants {

	public static final String MOD_ID = "qzisbundletweaks";
	public static final String MOD_NAME = "Qzimyion's Bundle tweaks";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
}
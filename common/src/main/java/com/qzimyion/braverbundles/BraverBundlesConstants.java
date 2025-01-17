package com.qzimyion.braverbundles;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BraverBundlesConstants {
	@ApiStatus.Internal
	public static final String PROJECT_ID = "Braver Bundles";
	@ApiStatus.Internal
	public static final String MOD_ID = "braverbundles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@ApiStatus.Internal
	@Contract("_ -> new")
	@NotNull
	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@ApiStatus.Internal
	@NotNull
	public static String string(String path) {
		return id(path).toString();
	}
}

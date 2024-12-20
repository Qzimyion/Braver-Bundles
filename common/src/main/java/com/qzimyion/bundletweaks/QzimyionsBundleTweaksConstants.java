package com.qzimyion.bundletweaks;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class QzimyionsBundleTweaksConstants {
	@ApiStatus.Internal
	public static final String PROJECT_ID = "Qzimyions's Bundle Tweaks";
	@ApiStatus.Internal
	public static final String MOD_ID = "qzisbundletweaks";

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

package com.qzimyion.braverbundles.fabric;

import com.qzimyion.braverbundles.common.BraverBundlesCommon;
import net.fabricmc.api.ModInitializer;

public class BraverBundlesFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		BraverBundlesCommon.init();
	}
}

package com.qzimyion.bundletweaks.fabric;

import com.qzimyion.bundletweaks.common.QzimyionsBundleTweaksCommon;
import net.fabricmc.api.ModInitializer;

public class QzimyionsBundleTweaksFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		QzimyionsBundleTweaksCommon.init();
	}
}

package com.qzimyion.bundletweaks.neoforge;

import com.qzimyion.bundletweaks.QzimyionsBundleTweaksConstants;
import com.qzimyion.bundletweaks.common.QzimyionsBundleTweaksCommon;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod(QzimyionsBundleTweaksConstants.MOD_ID)
public class QzimyionsBundleTweaksNeoForge {

	public QzimyionsBundleTweaksNeoForge(@NotNull IEventBus eventBus, ModContainer container) {
		QzimyionsBundleTweaksCommon.init();
	}
}

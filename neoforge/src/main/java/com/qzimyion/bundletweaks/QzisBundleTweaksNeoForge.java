package com.qzimyion.bundletweaks;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(QzisBundleTweaksConstants.MOD_ID)
public class QzisBundleTweaksNeoForge {

	public QzisBundleTweaksNeoForge(IEventBus eventBus) {
		QzisBundleTweaksCommon.init();
	}
}

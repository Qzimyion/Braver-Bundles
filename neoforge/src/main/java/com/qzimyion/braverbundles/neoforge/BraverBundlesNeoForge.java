package com.qzimyion.braverbundles.neoforge;

import com.qzimyion.braverbundles.BraverBundlesConstants;
import com.qzimyion.braverbundles.common.BraverBundlesCommon;
import com.qzimyion.braverbundles.config.CommonModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

@Mod(BraverBundlesConstants.MOD_ID)
public class BraverBundlesNeoForge {

	public BraverBundlesNeoForge(@NotNull IEventBus eventBus, ModContainer container) {
		BraverBundlesCommon.init();
		container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, arg) -> AutoConfig.getConfigScreen(CommonModConfig.class, arg).get());
	}
}

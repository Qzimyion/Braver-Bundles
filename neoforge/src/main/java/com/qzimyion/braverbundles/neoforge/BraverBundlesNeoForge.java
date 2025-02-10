package com.qzimyion.braverbundles.neoforge;

import com.qzimyion.braverbundles.BraverBundlesCommon;
import com.qzimyion.braverbundles.BraverBundlesConstants;
import eu.midnightdust.lib.config.MidnightConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(BraverBundlesConstants.MOD_ID)
public final class BraverBundlesNeoForge {
    public BraverBundlesNeoForge(ModContainer container) {
        BraverBundlesCommon.init();
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, arg) -> MidnightConfig.getScreen(arg, BraverBundlesConstants.MOD_ID));
    }
}

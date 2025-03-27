package com.qzimyion.braverbundles.neoforge;
import com.qzimyion.braverbundles.BraverBundlesClient;
import com.qzimyion.braverbundles.common.BraverBundlesCommon;
import com.qzimyion.braverbundles.BraverBundlesConstants;
import dev.architectury.platform.Platform;
import eu.midnightdust.lib.config.MidnightConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(BraverBundlesConstants.MOD_ID)
public final class BraverBundlesNeoForge {
    public BraverBundlesNeoForge(ModContainer container) {
        BraverBundlesCommon.init();
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, screen) -> MidnightConfig.getScreen(screen, BraverBundlesConstants.MOD_ID));
        if (Platform.getEnv()==Dist.CLIENT){
            BraverBundlesClient.init();
        }
    }
}

package com.qzimyion.braverbundles.neoforge;
import com.qzimyion.braverbundles.BraverBundlesClient;
import com.qzimyion.braverbundles.common.BraverBundlesCommon;
import com.qzimyion.braverbundles.BraverBundlesConstants;
import cpw.mods.modlauncher.Environment;
import dev.architectury.platform.Platform;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(BraverBundlesConstants.MOD_ID)
public final class BraverBundlesNeoForge {
    public BraverBundlesNeoForge() {
        BraverBundlesCommon.init();
        if (Platform.getEnv()== Dist.CLIENT){
            BraverBundlesClient.init();
        }
    }
}

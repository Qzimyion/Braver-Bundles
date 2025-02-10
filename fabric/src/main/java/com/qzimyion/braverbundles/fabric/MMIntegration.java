package com.qzimyion.braverbundles.fabric;

import com.qzimyion.braverbundles.BraverBundlesConstants;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;

public class MMIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, BraverBundlesConstants.MOD_ID);
    }
}

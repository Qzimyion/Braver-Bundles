package com.qzimyion.braverbundles.fabric;

import com.qzimyion.braverbundles.BraverBundlesClient;
import net.fabricmc.api.ClientModInitializer;

public class BraverBundlesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BraverBundlesClient.init();
    }
}

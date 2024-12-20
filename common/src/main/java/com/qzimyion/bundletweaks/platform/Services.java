package com.qzimyion.bundletweaks.platform;

import com.qzimyion.bundletweaks.QzisBundleTweaksConstants;
import com.qzimyion.bundletweaks.platform.services.IPlatformHelper;
import com.qzimyion.bundletweaks.platform.services.IRegistry;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistry REG_HELPER = load(IRegistry.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        QzisBundleTweaksConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
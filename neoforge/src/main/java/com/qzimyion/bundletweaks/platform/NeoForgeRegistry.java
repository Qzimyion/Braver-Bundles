package com.qzimyion.bundletweaks.platform;

import com.qzimyion.bundletweaks.QzisBundleTweaksConstants;
import com.qzimyion.bundletweaks.platform.services.IRegistry;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public class NeoForgeRegistry implements IRegistry {

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> entry) {
        T value = entry.get();
        Registry.register(registry, QzisBundleTweaksConstants.id(name), value);
        return ()-> value;
    }
}

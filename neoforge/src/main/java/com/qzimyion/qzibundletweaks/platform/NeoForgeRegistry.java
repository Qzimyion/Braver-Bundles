package com.qzimyion.qzibundletweaks.platform;

import com.qzimyion.qzibundletweaks.QzisBundleTweaksConstants;
import com.qzimyion.qzibundletweaks.platform.services.IRegistry;
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

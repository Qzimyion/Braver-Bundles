package com.qzimyion.bundletweaks.platform.services;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

public interface IRegistry {

    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> entry);
}

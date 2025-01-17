package com.qzimyion.braverbundles.config;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;

public class ConfigScreen {

	public static Screen createConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(CommonModConfig.class, parent).get();
	}
}

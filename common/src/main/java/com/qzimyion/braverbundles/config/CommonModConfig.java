package com.qzimyion.braverbundles.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "braverbundles")
public class CommonModConfig implements ConfigData {

	@ConfigEntry.Category("Main")
	@Comment(
		"""
			Allows you to store more unstackable items i.e. Pickaxes, Water buckets etc. inside a bundle.
			By default the mod allows you to store 4 more unstackables items in the bundle.

			Make sure to take out all the things from the bundle before resetting this value to prevent any glitches.
			"""
	)
	public static double UNSTACKING_FRACTIONS = 0.25D;

	@ConfigEntry.Category("Main")
	@Comment("Allays holding bundles can collect any items found inside.")
	public static boolean ALLAY_WHITELIST = true;

	@ConfigEntry.Category("Main")
	@Comment("Dispensers with a bundle inside can drop it's contents outside.")
	public static boolean DISPENSER_FUNC = true;

	@ConfigEntry.Category("Main")
	@Comment("If an item is in front of the dispenser with a bundle inside; the item is stored inside the bundle")
	public static boolean DISPENSER_ITEM_ENTITY_SCOOPING_IF_BUNDLE_INSIDE = true;
}

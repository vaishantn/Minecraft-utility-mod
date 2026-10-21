package net.prodragon.utilitymod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.prodragon.utilitymod.item.ModItems;
import net.prodragon.utilitymod.creativemodetab.ModCreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityMod implements ModInitializer {
	public static final String MOD_ID = "utilitymod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeModeTabs.registerModCreativeModeTabs();

		ModItems.registerModItems();

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}

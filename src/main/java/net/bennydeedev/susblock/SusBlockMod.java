package net.bennydeedev.susblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SusBlockMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "susblock";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Block SUS_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool());

	private static void registerBlock() {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "sus_block"), SUS_BLOCK);
	}

	private static void registerItem() {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "sus_block"),
				new BlockItem(SUS_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		registerBlock();
		registerItem();

		LOGGER.info("Registering Mod Items for " + MOD_ID);
	}
}

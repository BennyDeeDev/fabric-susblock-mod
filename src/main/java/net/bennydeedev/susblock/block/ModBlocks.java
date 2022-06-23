package net.bennydeedev.susblock.block;

import net.bennydeedev.susblock.SusBlockMod;
import net.bennydeedev.susblock.block.custom.SusBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block SUS_BLOCK = new SusBlock(
            FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool());

    public static void registerSusBlock() {
        Registry.register(Registry.BLOCK, new Identifier(SusBlockMod.MOD_ID, "sus_block"), SUS_BLOCK);
    }

    public static void registerSusBlockItem() {
        Registry.register(Registry.ITEM, new Identifier(SusBlockMod.MOD_ID, "sus_block"),
                new BlockItem(SUS_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
    }
}

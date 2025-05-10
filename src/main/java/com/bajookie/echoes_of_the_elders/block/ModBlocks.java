package com.bajookie.echoes_of_the_elders.block;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block EXPLORER_FRUIT_BLOCK = registerBlock("explorers_fruit_block", new FlowerBlock(StatusEffects.GLOWING, 1, FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block POTTED_EXPLORER_FRUIT_BLOCK = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_explorers_fruit_block"), new FlowerPotBlock(EXPLORER_FRUIT_BLOCK, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    public static final Block ARTIFACT_GEODE = registerBlock("artifact_geode_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_ORE).strength(2f), UniformIntProvider.create(2, 5)));

    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, new FabricItemSettings());
    }

    private static Block registerBlock(String name, Block block, FabricItemSettings settings) {
        // register block item
        Registry.register(Registries.ITEM, new Identifier(EOTE.MOD_ID, name), new BlockItem(block, settings));
        return Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, name), block);
    }


    public static void registerModBlocks() {
        EOTE.LOGGER.info("Registering blocks for ---> " + EOTE.MOD_ID);
    }

    public static void registerModBlocksModelLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(EXPLORER_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_EXPLORER_FRUIT_BLOCK, RenderLayer.getCutout());
    }
}

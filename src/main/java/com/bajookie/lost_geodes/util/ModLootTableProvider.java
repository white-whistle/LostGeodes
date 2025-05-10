package com.bajookie.lost_geodes.util;

import com.bajookie.lost_geodes.block.ModBlocks;
import com.bajookie.lost_geodes.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.entry.ItemEntry;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.EXPLORER_FRUIT_BLOCK, BlockLootTableGenerator.dropsWithShears(ModBlocks.EXPLORER_FRUIT_BLOCK, ItemEntry.builder(ModItems.EXPLORER_FRUIT)));
        addDrop(ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK, ModBlocks.EXPLORER_FRUIT_BLOCK);
        addDrop(ModBlocks.ARTIFACT_GEODE, BlockLootTableGenerator.dropsWithSilkTouch(ModBlocks.ARTIFACT_GEODE, ItemEntry.builder(ModItems.ARTIFACT_GEODE)));
    }

}
package com.bajookie.lost_geodes.world.gen;

import com.bajookie.lost_geodes.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModOreGeneration {
    public static void generateOres(){
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
               GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.ARTIFACT_GEODE_ORE_PLACED_KEY);
    }
}

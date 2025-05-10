package com.bajookie.lost_geodes.world;

import com.bajookie.lost_geodes.EOTE;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> EXPLORER_FRUIT_PLACED_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<PlacedFeature> ARTIFACT_GEODE_ORE_PLACED_KEY = registerKey("artifact_geode_ore_placed");


    public static void bootstrap(Registerable<PlacedFeature> context){
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        registerVegetationPlacedFeatures(context,configuredFeatureRegistryEntryLookup);
        registerOrePlacedFeatures(context,configuredFeatureRegistryEntryLookup);

    }


    public static void registerVegetationPlacedFeatures(Registerable<PlacedFeature> context, RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatureRegistryEntryLookup){
        register(context,EXPLORER_FRUIT_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.EXPLORERS_FRUIT_KEY),
                List.of(RarityFilterPlacementModifier.of(8) ,SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    }
    public static void registerOrePlacedFeatures(Registerable<PlacedFeature> context, RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatureRegistryEntryLookup){
        register(context,ARTIFACT_GEODE_ORE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ARTIFACT_GEODE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2 /* veins per chunk */,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(20)))//veins per chunk
        );
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
